package com.errorcodes.zyy_maintain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mt_user_group")
public class UserGroupPO {
    @TableId(value = "gid",type = IdType.AUTO)
    private String gid;
    @TableField(value = "gname")
    private String gname;
    @TableField(value = "has_admin_permission")
    private boolean hasAdminPermission;
}
