package com.errorcodes.zyy_maintain.controller;

import com.errorcodes.zyy_maintain.entity.RecordPO;
import com.errorcodes.zyy_maintain.entity.TimeStreamPO;
import com.errorcodes.zyy_maintain.entity.UserPO;
import com.errorcodes.zyy_maintain.service.RecordService;
import com.errorcodes.zyy_maintain.service.TimeStreamService;
import com.errorcodes.zyy_maintain.service.UserGroupService;
import com.errorcodes.zyy_maintain.service.UserService;
import com.errorcodes.zyy_maintain.websocket.WebSocketServerHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.*;

@Controller
@RequestMapping("/api/record")
@ResponseBody
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private TimeStreamService timeStreamService;


    @RequestMapping("/reportMaintenance")
    public Map<String,Object> reportMaintenance(String userToken, String title, String content, @RequestParam(required = false) String imgUrl){
        Map<String,Object> retMap = new HashMap<>();

        UserPO user = userService.getUserByToken(userToken);
        RecordPO reportMaintenanceEntity = new RecordPO();
        reportMaintenanceEntity.setTitle(title);
        reportMaintenanceEntity.setContent(content);
        Instant instant = Instant.now();
        reportMaintenanceEntity.setStartTime(instant.toString());
        reportMaintenanceEntity.setCreateUser(user.getUid());
        reportMaintenanceEntity.setCurrentStatus(1);
        // 判断是否有图片
        if (imgUrl != null){
            reportMaintenanceEntity.setImgUrl(imgUrl);
        }
        if (recordService.addRecord(reportMaintenanceEntity)) {
            retMap.put("code",200);
            retMap.put("msg","工单创建成功");
            // 发送WS信息打客户端（工单创建通知）
            WebSocketServerHandler.sendMessageToAllUser("用户 "+user.getUsername()+" 发布了一个报检工单，请及时完成或处理");

        } else {
            retMap.put("code",500);
            retMap.put("msg","工单创建失败，数据库返回错误");

        }

        return retMap;
    }
//
    @RequestMapping("/showAllMaintenance")
    public Map<String,Object> showAllMaintenance(){
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("code","200");
        retMap.put("msg","请求成功");
        List<RecordPO> recordPOS = recordService.queryAll();
        retMap.put("number",recordPOS.size());
        retMap.put("records",recordPOS);
        return retMap;
    }

    @RequestMapping("/queryMaintenanceByID")
    public Map<String,Object> showMaintenanceByID(String sid){
        Map<String,Object> retMap = new HashMap<>();
        List<RecordPO> recordPOS = recordService.queryByStatusID(Integer.parseInt(sid));
        if (recordPOS.size() > 0){
            retMap.put("code",200);
            retMap.put("msg","筛选成功");
            retMap.put("number",recordPOS.size());
            retMap.put("records",recordPOS);
        } else {
            retMap.put("code",505);
            retMap.put("msg","筛选为空");
            retMap.put("number",recordPOS.size());
            retMap.put("records",new ArrayList<>());
        }
        return retMap;
    }


    @RequestMapping("/queryMaintenance")
    public Map<String,Object> showMaintenanceByKeyWord(String keyWord){
        Map<String,Object> retMap = new HashMap<>();
        List<RecordPO> recordPOS = recordService.queryFuzzy(keyWord); // 模糊查询
        retMap.put("code","200");
        retMap.put("msg","请求成功");
        retMap.put("number",recordPOS.size());
        retMap.put("records",recordPOS);
        return retMap;
    }

    @RequestMapping("/examineMaintenance")
    public Map<String,Object> examineMaintenance(String rid,String sid,String userToken){
        HashMap<String,Object> retMap = new HashMap<>();
        // 判断用户token是否为管理员
        UserPO tokenUser = userService.getUserByToken(userToken);
        // 判断token是否有效（是否获取到正常的用户对象）
        if (tokenUser != null){
            // 将用户的gid传入到权限判断方法中进行判断
            if (userGroupService.hasAdminPermissionById(tokenUser.getUserGroup())){
                if (recordService.changeStatus(Integer.parseInt(rid),Integer.parseInt(sid))) {
                    retMap.put("code",200);
                    retMap.put("msg","状态审批成功");
                    // 发送WS信息打客户端（管理员审批通知）
                    JSONObject jsonObject = new JSONObject();
                    JSONObject recordJSON = new JSONObject();
                    RecordPO recordPO = recordService.queryByID(rid);
                    recordJSON.put("rid",recordPO.getRid());
                    recordJSON.put("startTime",recordPO.getStartTime());
                    recordJSON.put("endTime",recordPO.getEndTime());
                    recordJSON.put("title",recordPO.getTitle());
                    recordJSON.put("content",recordPO.getContent());
                    recordJSON.put("currentStatus",recordPO.getCurrentStatus());
                    recordJSON.put("createUser",recordPO.getCreateUser());
                    recordJSON.put("imgUrl",recordPO.getImgUrl());

                    jsonObject.put("record",recordJSON); // 返回当前通知的工单实体对象
                    jsonObject.put("code",1001);
                    jsonObject.put("notificationMessage","管理员 "+tokenUser.getUsername()+" 已经审批了工单状态，点击查看");
                    // 发送WS信息
                    WebSocketServerHandler.sendMessageToAllUser(jsonObject.toString());
                    // 修改事件流
                    UserPO gotUser = userService.getUserByToken(userToken);
                    timeStreamService.addTimeStream(Integer.parseInt(rid), gotUser.getUid(),Integer.parseInt(sid));
                } else {
                    retMap.put("code",500);
                    retMap.put("msg","状态审批失败，服务器内部错误");

                }
            }else {
                retMap.put("code",205);
                retMap.put("msg","拒绝审批，权限不足，请使用管理员用户组进行审批");
            }
        } else {
            retMap.put("code",405);
            retMap.put("msg","拒绝审批，使用了无效的token");

        }
        return retMap;
    }


    @RequestMapping("/timeStream")
    public Map<String,Object> getTimeStream(String rid){
        Map<String,Object> retMap = new HashMap<>();
        List<TimeStreamPO> recordTimeStream = timeStreamService.getRecordTimeStream(Integer.parseInt(rid));
        if (recordTimeStream.size() > 0){
            // 将获得的集合倒叙，让后面的先显示
            Collections.reverse(recordTimeStream);
            retMap.put("code",200);
            retMap.put("msg","请求成功");
            retMap.put("stream",recordTimeStream);
        } else {
            retMap.put("code",500);
            retMap.put("msg","查询失败，数据为空");
        }
        return retMap;
    }
}
