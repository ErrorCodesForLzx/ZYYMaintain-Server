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
@TableName("mt_user")
public class UserPO {
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "user_group")
    private int userGroup;
    @TableField(value = "token")
    private String token;

}
