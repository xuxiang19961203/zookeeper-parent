package com.xuxiang.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Xux
 * @package: com.xuxiang.pojo
 * @create: 2021/6/18
 * @FileName: TbUser
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbUser implements Serializable {

    private String username;
    private String password;
    private int uid;


}
