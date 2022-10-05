package com.errorcodes.zyy_maintain.controller;

import com.errorcodes.zyy_maintain.entity.FilesPO;
import com.errorcodes.zyy_maintain.service.FilesService;
import com.errorcodes.zyy_maintain.util.FileSystemLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/api/file")
@ResponseBody
public class FileUploadController {

    @Autowired
    private FilesService filesService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map<String, Object> uploadFile(@RequestParam("file")MultipartFile multipartFile){
        Map<String,Object> retMap = new HashMap<>();
        String originalFilename = multipartFile.getOriginalFilename();// 获取原文件名
        String suffixFileName   = originalFilename.substring(originalFilename.lastIndexOf(".")); // 获取文件拓展名
        suffixFileName = suffixFileName.toLowerCase(); // 将文本全部处理为小写
        if (!suffixFileName.equals(".jpg") && !suffixFileName.equals(".png")){ // 如果不是图片，不给予上传权限
            retMap.put("code",504);
            retMap.put("msg","请上传支持的图片类型：*.png *.jpg");
            retMap.put("result",null);
        } else { // 文件类型校验完成，准备上传
            String fileName = UUID.randomUUID().toString()+suffixFileName;// 本地服务器保存的文件名称
            File localFile = new File(FileSystemLoadUtil.getRunningDir()+File.separator+fileName);
            // 将获取到的文件上传到本地
            try {
                // 定义两个flag分别标识文件存在和数据库添加结果
                Boolean flagFileExist = false,flagDataBaseSuccess = false;
                multipartFile.transferTo(localFile);
                flagFileExist = localFile.exists();
                String strrr = multipartFile.getOriginalFilename();
                flagDataBaseSuccess = filesService.insertFile(multipartFile.getOriginalFilename(), fileName);
                if (flagFileExist && flagDataBaseSuccess){
                    retMap.put("code",200);
                    retMap.put("msg","文件上传成功");
                    retMap.put("fid",filesService.queryFileByUpLoadName(fileName).getFid());
                } else {
                    retMap.put("code",500);
                    retMap.put("msg","文件上传失败，服务器未检索到有效文件数据，可能文件损坏");
                }
            } catch (IOException e) {
                retMap.put("code",500);
                retMap.put("msg","文件上传失败，服务器内部错误");
                throw new RuntimeException(e);
            }

        }
        return retMap;
    }

    @RequestMapping(value = "/getPic",method = RequestMethod.GET,produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] getFile(String fid){
        FilesPO filesPO = filesService.queryFileById(fid); // 通过fid获取到
        if (filesPO != null){
            Path uploadPath = Paths.get(FileSystemLoadUtil.getRunningDir() + filesPO.getUploadName());
            byte[] byteDatas = null;
            try {
                byteDatas = Files.readAllBytes(uploadPath);
                return byteDatas;
            } catch (IOException e) {
                // 返回空对象
                return new byte[]{};
            }
        } else {
            // 返回空对象
            return new byte[]{};
        }
    }
}
