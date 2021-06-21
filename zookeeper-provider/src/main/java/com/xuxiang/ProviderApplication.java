package com.xuxiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Xux
 * @package: com.xuxiang
 * @create: 2021/6/7
 * @FileName: ProviderApplication
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.xuxiang")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
