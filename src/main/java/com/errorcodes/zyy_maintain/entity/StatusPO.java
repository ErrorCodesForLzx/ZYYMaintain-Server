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
@TableName("mt_status")
public class StatusPO {
    @TableId(value = "sid",type = IdType.AUTO)
    private Integer sid;
    @TableField(value = "sname")
    private String sname;
}
