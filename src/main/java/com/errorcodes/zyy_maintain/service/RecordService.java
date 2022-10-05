package com.errorcodes.zyy_maintain.service;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.errorcodes.zyy_maintain.entity.RecordPO;
import com.errorcodes.zyy_maintain.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;

    /**
     * 添加记录
     * @param record 记录实体
     * @return 返回执行结果
     */
    public boolean addRecord(RecordPO record) {
        return recordMapper.insert(record) >=1;
    }
    /**
     * 改变记录状态
     * @param recordId 记录ID
     * @param statusID 状态ID
     * @return 返回执行结果
     */
    public boolean changeStatus(Integer recordId,Integer statusID) {
        RecordPO recordPO = new RecordPO();
        recordPO.setRid(recordId);
        recordPO.setCurrentStatus(statusID);
        recordPO.setEndTime(Instant.now().toString()); // 加入结束时间（修改时间）
        return recordMapper.updateById(recordPO) >= 1;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<RecordPO> queryAll(){
        return recordMapper.selectList(null);
    }

    /**
     * 通过状态ID查询记录
     * @param sid 状态唯一标示
     * @return 返回查询到的记录
     */
    public List<RecordPO> queryByStatusID(Integer sid){
        QueryWrapper<RecordPO> wrapper = new QueryWrapper<>();
        wrapper.eq("current_status",sid.toString());
        return recordMapper.selectList(wrapper);
    }

    /**
     * 通过记录ID查询记录
     * @param rid 记录的唯一标示
     * @return 返回查询到的记录
     */
    public RecordPO queryByID(String rid){
        QueryWrapper<RecordPO> wrapper = new QueryWrapper<>();
        wrapper.eq("rid",rid);
        return recordMapper.selectOne(wrapper);
    }

    /**
     * 模糊查询记录
     * @param keyWord 关键字
     * @return 返回记录
     */
    public List<RecordPO> queryFuzzy(String keyWord){
        QueryWrapper<RecordPO> wrapper = new QueryWrapper<>();
        wrapper
                .like("title",keyWord).or().like("content",keyWord); // 模糊查询title和content中的字段
        return recordMapper.selectList(wrapper); // 返回查询结果
    }


}
