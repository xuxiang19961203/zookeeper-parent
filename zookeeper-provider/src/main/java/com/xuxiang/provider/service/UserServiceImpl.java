package com.xuxiang.provider.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuxiang.dao.UserMapper;
import com.xuxiang.pojo.User;
import com.xuxiang.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.provider.serivce
 * @create: 2021/6/7
 * @FileName: UserServiceImpl
 * @Description:
 */

@Service(version = "1.0")
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserMapper UserMapper;

    @Override
    public List<User> selectAllUser() {
        return UserMapper.selectAllUser();
    }

    @Override
    public PageInfo<User> selectUserByPageHelper(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(1, 10);
        List<User> users = UserMapper.selectAllUser();
        PageInfo PageInfo = new PageInfo(users);
        return PageInfo;
    }

}
