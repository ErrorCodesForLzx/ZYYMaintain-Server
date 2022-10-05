package com.errorcodes.zyy_maintain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.errorcodes.zyy_maintain.entity.StatusPO;
import com.errorcodes.zyy_maintain.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusMapper statusMapper;

    /**
     * 通过状态ID获取状态实体对象
     * @param sid 状态ID
     * @return 返回对象
     */
    public StatusPO getStatusById(int sid){
        QueryWrapper<StatusPO> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",sid);
        return statusMapper.selectOne(wrapper);
    }

    /**
     * 获取所有状态
     * @return 返回状态集
     */
    public List<StatusPO> getAllStatus(){
        return statusMapper.selectList(new QueryWrapper<StatusPO>());
    }
}
