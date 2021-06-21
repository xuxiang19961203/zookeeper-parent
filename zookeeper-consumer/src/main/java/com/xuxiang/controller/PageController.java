package com.xuxiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Xux
 * @package: com.xuxiang.controller
 * @create: 2021/6/13
 * @FileName: PageController
 * @Description:
 */
@Controller
public class PageController {

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

    @RequestMapping("/{page}")
    public String toPage(@PathVariable String page){
        if (page==null||"".equals(page)) {
            page = "index";
        }
        return page;
    }
}
