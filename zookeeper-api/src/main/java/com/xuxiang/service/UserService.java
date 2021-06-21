package com.xuxiang.service;

import com.github.pagehelper.PageInfo;
import com.xuxiang.pojo.User;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.service
 * @create: 2021/6/7
 * @FileName: UserService
 * @Description:
 */
public interface UserService {

    List<User> selectAllUser();

    PageInfo<User> selectUserByPageHelper(Integer pageNum,Integer pageSize);
}