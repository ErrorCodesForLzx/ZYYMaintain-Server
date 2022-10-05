package com.errorcodes.zyy_maintain.websocket;

import com.errorcodes.zyy_maintain.entity.UserPO;
import com.errorcodes.zyy_maintain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/broadcast/{connectUserId}/{connectUserToken}")
@Component
@Slf4j
public class WebSocketServerHandler {

    // 注入用户Service对象

    private UserService userService = new UserService();

    // 与某一个客户端的连接会话，是和客户端通讯的唯一凭证
    private Session session;
    // 某一个客户端的用户ID和token令牌
    private Integer thisUserId;
    private String  thisUserToken;
    // 一个静态Session集合，用于存放所有连接的用户会话对象
    private static ConcurrentHashMap<Integer,Session> sessionPool = new ConcurrentHashMap<>();
    // 用来存放客户端一对一的Handler对象
    private static CopyOnWriteArraySet<WebSocketServerHandler> webSocketSets = new CopyOnWriteArraySet<>();


    @OnOpen
    public void onOpen(Session clientSession, @PathParam(value = "connectUserId")Integer userId,@PathParam(value = "connectUserToken") String userToken){
        log.info("用户[{}]和服务器建立WS握手",userId);
        this.thisUserId    = userId;
        this.thisUserToken = userToken;

        Session historySession = sessionPool.get(userId); // 尝试在会话池里面搜索
        // 如果不为空，代表有其他的客户端正在连接
        if (historySession != null) {
            log.warn("用户[{}]和服务器建立了重复的握手,尝试和重复用户断开连接",userId);
            try {
                sessionPool.remove(userId); // 移除连接的会话对象
                historySession.close(); // 主动和客户端发送分手报文,并和客户端断开会话连接
            } catch (IOException e) {
                log.error("重复登录异常，异常信息："+e.getMessage(),e);
                // 抛出错误异常
            }
        }
        // 建立连接
        this.session = clientSession;    // 将方法传递的Session写到全局变量里面
        webSocketSets.add(this);         // 客户端一对一服务端对象添加到服务器对象集合里面
        sessionPool.put(userId,session); // 将客户端的连接会话对象添加到客户端会话集里面
        log.info("用户[{}]已连接，上线用户人数:{}",userId,webSocketSets.size());

//        // 先在数据库里面比对用户信息
//        UserPO tokenUser = userService.getUserByToken(userToken);
//        if (tokenUser != null) {
//            if (Objects.equals(tokenUser.getUid(), userId)){
//
//
//
//            } else {
//                log.warn("客户端传递的用户和token令牌无法比对");
//            }
//        } else {
//            log.warn("客户端传递了无效的token令牌");
//        }

    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable){
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose(){
        webSocketSets.remove(this); // 移除一对一连接对象
        log.info("用户[{}]断开连接，上线用户人数为:{}",this.thisUserId,webSocketSets.size());
    }

    /**
     * 服务器接收到客户端发送的消息
     * @param reservedMessage 接收的消息
     */
    @OnMessage
    public void onMessage(String reservedMessage){
        log.info("{}说:{}",thisUserId,reservedMessage);
    }

    /**
     * 用于服务器主动向某个客户端推送消息
     * @param userId 某个用户ID
     * @param sendMessageText 推送的消息
     */
    public static void sendMessageToOneUser(Integer userId,String sendMessageText){
        log.info("服务器给[{}]说:{}",userId,sendMessageText);
        Session session = sessionPool.get(userId);
        try {
            session.getBasicRemote().sendText(sendMessageText); // 发送消息
        } catch (IOException e) {
            log.error("服务器发送消息错误:"+e.getMessage(),e);
        }
    }
    /**
     * 用于服务器主动向所有客户端发送消息
     * @param sendMessageText 推送的消息
     */
    public static void sendMessageToAllUser(String sendMessageText){
        log.info("服务器给所有用户说:{}",sendMessageText);
        for (WebSocketServerHandler webSocketServerHandler:webSocketSets){
            try {
                webSocketServerHandler.session.getBasicRemote().sendText(sendMessageText); // 发送消息
            } catch (IOException e) {
                log.error("服务器发送消息错误:"+e.getMessage(),e);
            }
        }
    }
}
