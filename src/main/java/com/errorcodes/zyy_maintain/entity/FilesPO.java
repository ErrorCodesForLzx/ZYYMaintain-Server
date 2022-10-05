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
@TableName("mt_files")
public class FilesPO {
    @TableId(value = "fid",type = IdType.AUTO)
    private Integer fid;
    @TableField(value = "file_name")
    private String fileName;
    @TableField(value = "upload_name")
    private String uploadName;
}
