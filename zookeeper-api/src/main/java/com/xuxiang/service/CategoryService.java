package com.xuxiang.service;

import com.xuxiang.pojo.Goods_category;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.service
 * @create: 2021/6/12
 * @FileName: CategoryService
 * @Description:
 */
public interface CategoryService {

    List<Goods_category> getCategoryListByParentId(Short parentId);


    List<Goods_category> getAllCategoryList();

    String delGoodsCategoryById(Short id);

    String insertCategory(Goods_category category);

    Goods_category getCategory(Short id);

}