package com.errorcodes.zyy_maintain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.errorcodes.zyy_maintain.entity.FilesPO;
import com.errorcodes.zyy_maintain.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FilesService {
    @Autowired
    private FilesMapper filesMapper;

    /**
     * 添加一个文件到数据库
     * @param fileName 文件名称
     * @param upLoadPath 上传文件名称
     * @return 返回添加结果
     */
    public boolean insertFile(String fileName,String upLoadPath){
        FilesPO filesPO = new FilesPO();
        filesPO.setFileName(fileName);
        filesPO.setUploadName(upLoadPath);
        return filesMapper.insert(filesPO) >= 1;
    }

    /**
     * 查询文件通过文件ID
     * @param fid 文件ID
     * @return 返回文件实体对象
     */
    public FilesPO queryFileById(String fid){
        QueryWrapper<FilesPO> wrapper = new QueryWrapper<>();
        wrapper.eq("fid",fid);
        return filesMapper.selectOne(wrapper);
    }
    /**
     * 查询文件通过文件上传名
     * @param uploadName 文件上传名
     * @return 返回文件实体对象
     */
    public FilesPO queryFileByUpLoadName(String uploadName){
        QueryWrapper<FilesPO> wrapper = new QueryWrapper<>();
        wrapper.eq("upload_name",uploadName);
        return filesMapper.selectOne(wrapper);
    }
}
