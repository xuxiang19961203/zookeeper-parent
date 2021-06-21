package com.xuxiang.service.Impl;

import com.alibaba.fastjson.JSON;
import com.xuxiang.common.pojo.TaotaoResult;
import com.xuxiang.common.pojo.TbUser;
import com.xuxiang.dao.UserMapper;
import com.xuxiang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: Xux
 * @package: com.xuxiang.service.Impl
 * @create: 2021/6/18
 * @FileName: UserServiceImpl
 * @Description:
 */
@Service
@Slf4j
@PropertySource("classpath:config/userlogin.properties")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper UserMapper;

    private ValueOperations<String, String> StringValueOperations;

    private RedisTemplate<String, String> RedisTemplate;

    //用户信息存入redis时的key
    @Value("${USER_INFO}")
    private String USER_INFO;
    //用户信息存入redis时的key的有效时间
    @Value("${EXPIRE_TIME}")
    private int EXPIRE_TIME;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> RedisTemplate) {
        this.RedisTemplate = RedisTemplate;
        this.StringValueOperations = RedisTemplate.opsForValue();
    }


    //登录
    @Override
    public TaotaoResult selectTbUser(String username, String password) {
        //根据用户名查出用户
        TbUser tbUser = UserMapper.selectTbUser(username);
        if (tbUser == null) {
            return TaotaoResult.build(400, "用户名或密码错误!");
        }
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5Password.equals(tbUser.getPassword())) {
            return TaotaoResult.build(400, "密码错误!");
        }
        //如果登录成功,生成token
        String token = UUID.randomUUID().toString();
        log.info("token:{}", token);
        //隐藏user的密码信息
        tbUser.setPassword(null);
        String jsonString = JSON.toJSONString(tbUser);
        log.info(jsonString);
        StringValueOperations.set(USER_INFO + ":" + token, jsonString, EXPIRE_TIME, TimeUnit.SECONDS);
        TaotaoResult ok = TaotaoResult.ok(token);
        return ok;
    }

    //注册
    @Override
    public TaotaoResult insertTbUser(TbUser TbUser) {
        String md5Password = DigestUtils.md5DigestAsHex(TbUser.getPassword().getBytes());
        //将密码加密
        System.out.println("md5Password = " + md5Password);
        TbUser.setPassword(md5Password);
        int i = UserMapper.insertTbUser(TbUser);
        return i > 0 ? TaotaoResult.ok("注册成功!") : TaotaoResult.build(400, "注册失败!");
    }

    //校验
    @Override
    public TaotaoResult getTbUserByToken(String token) {
        String userKey = USER_INFO + ":" + token;
        String strJson = StringValueOperations.get(userKey);
        //如果查询到用户信息,则转成对象并保存,并刷新redis的expire
        if (strJson != null && strJson != "") {
            TbUser tbUser = JSON.parseObject(strJson, TbUser.class);
            //刷新redis存活时间
            StringValueOperations.set(userKey, strJson, EXPIRE_TIME, TimeUnit.SECONDS);
            return TaotaoResult.ok(tbUser);
        }
        return TaotaoResult.build(400, "用户未登录或闲置时间过长");
    }
}
