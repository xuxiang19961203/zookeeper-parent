package com.xuxiang.controller;

import com.github.pagehelper.PageInfo;
import com.xuxiang.pojo.User;
import com.xuxiang.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.controller
 * @create: 2021/6/7
 * @FileName: UserController
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Reference(version = "1.0")
    private UserService UserService;

    @RequestMapping("userList")
    @ResponseBody
    public List<User> selectAllUser(){

        return UserService.selectAllUser();
    }

    @RequestMapping("userListByPage")
    @ResponseBody
    public ModelAndView selectUserByPageHelper(){
        ModelAndView Mv = new ModelAndView();
        PageInfo<User> PageInfo = UserService.selectUserByPageHelper(1, 10);
        Mv.setViewName("userlist");
        Mv.addObject("PageInfo",PageInfo);
        return Mv;
    }
}
