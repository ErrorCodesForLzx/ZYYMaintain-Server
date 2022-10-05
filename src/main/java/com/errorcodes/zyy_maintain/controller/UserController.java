package com.errorcodes.zyy_maintain.controller;

import com.errorcodes.zyy_maintain.entity.UserPO;
import com.errorcodes.zyy_maintain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserName")
    public Map<String,Object> getUserName(String uid){
        UserPO userById = userService.getUserById(uid);
        Map<String,Object> retMap = new HashMap<>();
        if (userById != null){
            retMap.put("code","200");
            retMap.put("msg","请求成功");
            retMap.put("userName",userById.getUsername());
        } else {
            retMap.put("code","405");
            retMap.put("msg","请求失败");
            retMap.put("userName",null);
        }
        return retMap;
    }

}
