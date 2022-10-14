package com.errorcodes.zyy_maintain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mt_timestream")
public class TimeStreamPO {
    @TableId(value = "tid",type = IdType.AUTO)
    private Integer tid;
    @TableField(value = "record")
    private Integer record;
    @TableField(value = "execute_user")
    private Integer executeUser;
    @TableField(value = "execute_time")
    private String executeTime;
    @TableField(value = "execute_status")
    private Integer executeStatus;
}
