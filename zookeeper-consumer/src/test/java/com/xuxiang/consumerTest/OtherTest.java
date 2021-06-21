package com.xuxiang.consumerTest;

import com.xuxiang.pojo.User;
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
 * @FileName: OtherTest
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OtherTest {

    private static final Logger logger = LoggerFactory.getLogger(OtherTest.class);

    @Test
    public void testLogger(){
        User u = new User("徐想", 1);
        logger.info("UID:{} username:{}", u.getUid(), u.getUsername());
    }

}
