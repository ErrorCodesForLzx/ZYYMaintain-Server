package com.errorcodes.zyy_maintain;

import com.errorcodes.zyy_maintain.util.FileSystemLoadUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
@MapperScan("com.errorcodes.zyy_maintain.mapper")
public class ZyyMaintainApplication {

    /*
    Author:雷智翔
    本软件开源免费，谢谢您下载本源码
     */

    // 程序执行入口
    public static void main(String[] args) {
        // 启动服务器
        SpringApplication.run(ZyyMaintainApplication.class, args);
        // 加载文件系统支持对象
        FileSystemLoadUtil.getRunningDir();
    }

}
