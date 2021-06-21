package com.xuxiang.controller;

import com.xuxiang.pojo.Goods_category;
import com.xuxiang.pojo.PageResultBean;
import com.xuxiang.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.controller
 * @create: 2021/6/12
 * @FileName: CategoryController
 * @Description:
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Reference
    private com.xuxiang.service.CategoryService CategoryService;

    @Reference
    private SearchService SearchService;


    //初始化
    @RequestMapping("/initAllToSolr")
    @ResponseBody
    public String initAllToSolr() {
        String result = SearchService.initAllToSolr();
        logger.info("初始化成功!");
        return result;
    }

    //查询
    @RequestMapping("/queryByKeywords")
    public String queryByKeywords(Model model, String keywords) {
        List<Goods_category> categoryList = SearchService.queryByKeywords(keywords);
        model.addAttribute("categoryList", categoryList);
        return "category-list";
    }

    //分页查询和高亮显示
    @RequestMapping("/queryByKeywords3")
    public String queryByKeywords3(Model model, @RequestParam(defaultValue = "test")
            String keywords, Integer pageStart, Integer pageRows) {
        PageResultBean<Goods_category> PageResultBean = SearchService.queryByKeywordsWithHighLight(keywords, pageStart, pageRows);
        model.addAttribute("PageResultBean", PageResultBean);
        return "category-list2";  //已分页
    }

    //保存到solr
    @RequestMapping("/saveKeywords")
    public String saveKeywords(Goods_category category) {
        String result = SearchService.saveKeywords(category);
        logger.info("result =" + result);
        return "category-list";
    }

    //新增Category到数据库
    @RequestMapping("/saveCategory")
    @ResponseBody
    public String saveCategory() {
        Goods_category category = new Goods_category();
        category.setName("测试测试新增");
        category.setId((short) 99);
        category.setParentId((short) 0);
        String result = CategoryService.insertCategory(category);
        return result;
    }

    //从数据库删除Category
    @RequestMapping("/delCategory")
    @ResponseBody
    public String delCategory(short id) {
        String result = CategoryService.delGoodsCategoryById(id);
        return result;
    }

}
