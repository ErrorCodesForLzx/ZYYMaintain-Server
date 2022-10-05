package com.errorcodes.zyy_maintain.controller;

import com.errorcodes.zyy_maintain.entity.StatusPO;
import com.errorcodes.zyy_maintain.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/status")
@ResponseBody
public class StatusController {

    @Autowired
    private StatusService statusService;

    @RequestMapping("/getStatus")
    public Map<String,Object> getStatus(String sid){
        Map<String,Object> retMap = new HashMap<>();
        StatusPO statusPO = statusService.getStatusById(Integer.parseInt(sid));
        if (statusPO != null){
            retMap.put("code","200");
            retMap.put("msg","请求成功");
            retMap.put("statusName",statusPO.getSname());
        } else {
            retMap.put("code","405");
            retMap.put("msg","请求失败");
            retMap.put("statusName",null);
        }
        return retMap;
    }
    @RequestMapping("/getAllStatus")
    public Map<String,Object> getAllStatus(){
        Map<String,Object> retMap = new HashMap<>();
        List<StatusPO> allStatus = statusService.getAllStatus();
        if (allStatus.size() > 0){
            retMap.put("code",200);
            retMap.put("msg","请求成功");
            retMap.put("status",allStatus);
        } else {
            retMap.put("code",500);
            retMap.put("msg","查询为空");
            retMap.put("status",new ArrayList<String>());
        }
        return retMap;
    }

}
