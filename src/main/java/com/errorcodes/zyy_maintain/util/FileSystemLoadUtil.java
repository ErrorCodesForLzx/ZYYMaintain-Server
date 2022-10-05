package com.errorcodes.zyy_maintain.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

/**
 * 初始化文件系统
 */
public class FileSystemLoadUtil {
    private static String runningDir;
    static {
        System.out.println("-----------------------------");
        System.out.println("|           FS V1.0         |");
        System.out.println("|   UPLOAD PIC FILE SUPPORT |");
        System.out.println("-----------------------------");
        try {
            runningDir = ResourceUtils.getURL("classpath:").getPath()+"uploads"+File.separator;
            File file = new File(runningDir);
            if (!file.exists()){
                file.mkdir();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------------------");
        System.out.println("Local JAR package file dir:"+runningDir);
        System.out.println("------------------------------");
    }


    public  static String getRunningDir(){
        return runningDir;
    }
}
