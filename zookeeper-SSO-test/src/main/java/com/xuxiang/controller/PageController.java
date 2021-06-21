package com.xuxiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Xux
 * @package: com.xuxiang.controller
 * @create: 2021/6/19
 * @FileName: PageController
 * @Description:
 */
@Controller
public class PageController {

    @RequestMapping("/page/{page}")
    public String toPage(@PathVariable String page){
        return page;
    }
}
