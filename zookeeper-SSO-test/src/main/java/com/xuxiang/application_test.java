package com.xuxiang;

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
public class application_test {
    public static void main(String[] args) {
        System.out.println("测试application");
        SpringApplication.run(application_test.class,args);
    }
}
