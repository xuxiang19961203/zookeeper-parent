package com.xuxiang.dao;

import com.xuxiang.pojo.User;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang
 * @create: 2021/6/7
 * @FileName: UserMapper
 * @Description:
 */
//@Mapper    //Applicaton 扫描全部包mapperScan
public interface UserMapper {

    List<User> selectAllUser();

    List<User> selectUserByPageHelper();
}