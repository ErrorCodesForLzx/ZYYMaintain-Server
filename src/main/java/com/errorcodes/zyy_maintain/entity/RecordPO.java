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
@TableName("mt_record")
public class RecordPO {
    @TableId(value = "rid",type = IdType.AUTO)
    private Integer rid;
    @TableField(value = "start_time")
    private String startTime;
    @TableField(value = "end_time")
    private String endTime;
    @TableField(value = "title")
    private String title;
    @TableField(value = "content")
    private String content;
    @TableField(value = "current_status")
    private Integer currentStatus;
    @TableField(value = "create_user")
    private Integer createUser;
    @TableField(value = "img_url")
    private String imgUrl;
}
