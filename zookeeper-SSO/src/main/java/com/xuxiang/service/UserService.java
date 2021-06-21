package com.xuxiang.service;

import com.xuxiang.common.pojo.TaotaoResult;
import com.xuxiang.common.pojo.TbUser;

/**
 * @author: Xux
 * @package: com.xuxiang.service
 * @create: 2021/6/18
 * @FileName: UserService
 * @Description:
 */
public interface UserService {

    TaotaoResult selectTbUser(String username, String password);

    TaotaoResult insertTbUser(TbUser TbUser);

    TaotaoResult getTbUserByToken(String token);
}