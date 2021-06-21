package com.xuxiang.service;

import com.xuxiang.pojo.Goods_category;
import com.xuxiang.pojo.PageResultBean;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.service
 * @create: 2021/6/13
 * @FileName: SearchService
 * @Description:
 */
public interface SearchService {

    //普通模式分页
    PageResultBean<Goods_category> queryByKeywords(String keywords, Integer pageStart, Integer pageRows) ;

    //高亮显示
    PageResultBean<Goods_category> queryByKeywordsWithHighLight(String keywords, Integer pageStart, Integer pageRows) ;

    //普通查询
    List<Goods_category> queryByKeywords(String keywords);

    //保存
    String saveKeywords(Goods_category category);

    //加载全部数据到solr
    String  initAllToSolr();

}
