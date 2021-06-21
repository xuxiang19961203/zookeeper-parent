package com.xuxiang.controller;

import com.alibaba.fastjson.JSON;
import com.xuxiang.common.pojo.TaotaoResult;
import com.xuxiang.common.pojo.TbUser;
import com.xuxiang.common.utils.CookieUtils;
import com.xuxiang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
@PropertySource("classpath:config/userLoginCookie.properties")
@Slf4j
public class UserController {

    @Autowired
    private UserService UserService;

    //响应到用户客户端的 cookie的key
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    //登录
    @RequestMapping("/login")
    @ResponseBody
    public TaotaoResult Login(HttpServletRequest request, HttpServletResponse response, String username, String password) {

        String serverName = request.getRequestURL().toString();
        log.info("serverName = " + serverName);
        TaotaoResult result = UserService.selectTbUser(username, password);
        if (result.getStatus() == 200) {
            //将用户数据保存到cookies中存活10分钟
            CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString(), 600);
        }
        return result;
    }

    //注册
    @RequestMapping("/register")
    @ResponseBody
    public TaotaoResult register(TbUser TbUser) {
        return UserService.insertTbUser(TbUser);
    }

    //检验token是否有效
    @GetMapping("/token/{token}")
    @ResponseBody
    public Object register(@PathVariable String token, String callback) {

        TaotaoResult result = UserService.getTbUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            //第1种方式,拼接jsonp .如果是jsonp 需要拼接 类似于fun({id:1});
            String jsonpstr = callback + "(" + JSON.toJSONString(result) + ")";
            return jsonpstr;
            /*
            //第二种方式，使用Spring自带对象，前提是需要在Srping4.0的版本才有的哟。
            MappingJacksonValue value = new MappingJacksonValue(result);
            value.setJsonpFunction(callback);
            return value;*/
        }
        //如果不是jsonp类型,则返回默认json格式
        return JSON.toJSONString(result);
    }
}
