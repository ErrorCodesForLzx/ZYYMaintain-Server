package com.errorcodes.zyy_maintain.controller;

import com.errorcodes.zyy_maintain.entity.UserPO;
import com.errorcodes.zyy_maintain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/auth/user")
@ResponseBody
public class UserAuthenticationController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public Map<String,Object> authenticationUser(String userName, String passWord){
        HashMap<String,Object> retMap = new HashMap<>();
        UserPO userPO = userService.queryUser(userName, passWord);
        if (userPO != null) {
            retMap.put("msg","用户登录成功");
            retMap.put("code",200);
            HashMap<String,Object> authResult = new HashMap<>();
            authResult.put("token",userPO.getToken());
            authResult.put("uid",userPO.getUid());
            Instant instant = Instant.now();
            authResult.put("timestamp",instant.toString());
            authResult.put("isLogin",true);
            retMap.put("authResult",authResult);

        } else {
            retMap.put("msg","登录失败，用户名密码无法比对");
            retMap.put("code",502);
            HashMap<String,Object> authResult = new HashMap<>();
            authResult.put("token",null);
            Instant instant = Instant.now();
            authResult.put("timestamp",instant.toString());
            authResult.put("isLogin",false);
            retMap.put("authResult",authResult);
        }
        return retMap;
    }

    /**
     * 与服务器认证token
     * @param token 请求的token
     * @param userName token对应的用户名
     * @param time 时间戳
     * @return 返回认证结果
     */
    @RequestMapping("/authToken")
    public Map<String,Object> authenticationToken(String token,String userName,String time){
        Map<String,Object> retMap = new HashMap<>();
        UserPO gotUser = userService.getUserByToken(token);
        if (gotUser != null){
            String username = gotUser.getUsername();
            if (username.equals(userName) && token.equals(gotUser.getToken())){
                retMap.put("code",200);
                retMap.put("msg","token比对成功");
                Map<String,String> checks = new HashMap<>();
                checks.put("serverToken",gotUser.getToken());
                checks.put("clientToken",token);
                checks.put("tokenUID",String.valueOf(gotUser.getUid()));
                retMap.put("checks",checks);
            } else {
                retMap.put("code",500);
                retMap.put("msg","token比对失败");
                Map<String,String> checks = new HashMap<>();
                checks.put("serverToken","***");
                checks.put("clientToken",token);
                retMap.put("checks",checks);
            }
        } else {
            retMap.put("code",405);
            retMap.put("msg","token比对失败");
            Map<String,String> checks = new HashMap<>();
            checks.put("serverToken","***");
            checks.put("clientToken",token);
            retMap.put("checks",checks);
        }
        return retMap;
    }
}
