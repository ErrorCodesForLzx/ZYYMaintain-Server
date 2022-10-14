package com.errorcodes.zyy_maintain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.errorcodes.zyy_maintain.entity.RecordPO;
import com.errorcodes.zyy_maintain.entity.TimeStreamPO;
import com.errorcodes.zyy_maintain.mapper.TimeStreamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class TimeStreamService {
    @Autowired
    private TimeStreamMapper timeStreamMapper;

    /**
     * 获取一条工单的事件流
     * @param rid 记录ID
     * @return 返回事件流集合
     */
    public List<TimeStreamPO> getRecordTimeStream(Integer rid){
        QueryWrapper<TimeStreamPO> wrapper = new QueryWrapper<>();
        wrapper.eq("record",rid);
        return timeStreamMapper.selectList(wrapper);
    }

    public boolean addTimeStream(Integer rid,Integer executeUser,Integer executeStatus){
        TimeStreamPO timeStreamPO = new TimeStreamPO();
        timeStreamPO.setRecord(rid);
        timeStreamPO.setExecuteUser(executeUser);
        timeStreamPO.setExecuteStatus(executeStatus);
        Instant instant = Instant.now();
        timeStreamPO.setExecuteTime(instant.toString());
        return timeStreamMapper.insert(timeStreamPO)>0;
    }
}
