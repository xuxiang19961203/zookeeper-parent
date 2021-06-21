package com.xuxiang.consumerTest;

import com.github.pagehelper.PageInfo;
import com.xuxiang.pojo.User;
import com.xuxiang.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: Xux
 * @package: com.xuxiang.consumerTest
 * @create: 2021/6/8
 * @FileName: ConsumerTest
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ConsumerTest {

    @Reference(version = "1.0")
    private UserService UserService;

    private  static Logger logger = LoggerFactory.getLogger(ConsumerTest.class);

    @Test
    public void findAllByPageHelper(){
        PageInfo<User> userPageInfo = UserService.selectUserByPageHelper(1, 10);
        logger.info("userPageInfo.getPages() =" + userPageInfo.getPages());
        logger.info("userPageInfo.getSize() =" + userPageInfo.getSize());
    }
}
