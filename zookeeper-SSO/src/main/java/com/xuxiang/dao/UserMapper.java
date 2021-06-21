package com.xuxiang.dao;


import com.xuxiang.common.pojo.TbUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author: Xux
 * @package: com.xuxiang.dao
 * @create: 2021/6/18
 * @FileName: UserMapper
 * @Description:
 */
@Component
public interface UserMapper {


    TbUser selectTbUser(@Param("username") String username);

    int insertTbUser(TbUser TbUser);
}