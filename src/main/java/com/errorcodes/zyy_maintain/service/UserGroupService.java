package com.errorcodes.zyy_maintain.service;


import com.errorcodes.zyy_maintain.entity.UserGroupPO;
import com.errorcodes.zyy_maintain.mapper.UserGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupMapper userGroupMapper;

    /**
     * 通过用户组ID获取用户
     * @param gid 组ID
     * @return 返回获取到的用户组
     */
    public UserGroupPO getUserGroupById(Integer gid){
        return userGroupMapper.selectById(gid);
    }

    /**
     * 通过用户组ID查询用户是否具有管理员权限
     * @param gid 用户组ID
     * @return 返回是否具有管理员权限
     */
    public boolean hasAdminPermissionById(Integer gid){
        UserGroupPO user = getUserGroupById(gid); // 通过用户组ID获取到用户实体对象
        return user.isHasAdminPermission(); // 获取用户组权限字段
    }




}
