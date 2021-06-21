package com.xuxiang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: Xux
 * @package: com.xuxiang.pojo
 * @create: 2021/6/7
 * @FileName: User
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String username;
    private int uid;
}
