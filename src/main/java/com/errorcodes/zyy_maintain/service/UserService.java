package com.errorcodes.zyy_maintain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.errorcodes.zyy_maintain.entity.UserPO;
import com.errorcodes.zyy_maintain.mapper.UserMapper;
import com.errorcodes.zyy_maintain.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Wrapper;
import java.time.Instant;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过token获取用户
     * @param token token
     * @return
     */
    public UserPO getUserByToken(String token){
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("token",token);
        return userMapper.selectOne(wrapper);
    }
    /**
     * 通过用户ID获取用户
     * @param uid uid
     * @return
     */
    public UserPO getUserById(String uid){
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid);
        return userMapper.selectOne(wrapper);
    }

    public boolean checkToken(String token,String userName){
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.eq("token",token);
        wrapper.eq("username",userName);
        return userMapper.selectOne(wrapper) != null;
    }

    public UserPO queryUser(String userName,String passWord){
        QueryWrapper<UserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userName);// 将参数创建为一个虚拟用户实体
        queryWrapper.eq("password",passWord);
        UserPO selectedUser = userMapper.selectOne(queryWrapper);
        if (selectedUser != null) {
            Instant instant = Instant.now();
            try {
                String generatedToken = MD5Util.md5(instant.toString() + userName);
                selectedUser.setToken(generatedToken);
                UserPO userPO = new UserPO();
                userPO.setToken(generatedToken);
                userPO.setUserGroup(selectedUser.getUserGroup());
                UpdateWrapper<UserPO> userPOUpdateWrapper = new UpdateWrapper<>();
                userPOUpdateWrapper.eq("username",userName);
                userPOUpdateWrapper.eq("password",passWord);
                userMapper.update(userPO, userPOUpdateWrapper);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return selectedUser;
        }
        return  null;
    }

}
