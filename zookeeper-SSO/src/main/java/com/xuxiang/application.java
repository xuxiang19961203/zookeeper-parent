package com.xuxiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Xux
 * @package: com.xuxiang
 * @create: 2021/6/18
 * @FileName: application
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.xuxiang.dao")
public class application {
    public static void main(String[] args) {
        SpringApplication.run(application.class,args);
    }
}
