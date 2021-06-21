package com.xuxiang.config;

import com.xuxiang.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Xux
 * @package: com.xuxiang.config
 * @create: 2021/6/18
 * @FileName: DIYBeansConfig
 * @Description:
 */
@Configuration  //代表这是一个配置类, 相当于创建了一个 spring.xml
public class DIYBeansConfig {

    //相当于把bean的创建交给spring
    @Bean
    public User user(){  //方法的名称有规范, 相当于创建了一个  <bean  id="user" class=... >
        return new User("user-diy-config", 123);
    }
}
