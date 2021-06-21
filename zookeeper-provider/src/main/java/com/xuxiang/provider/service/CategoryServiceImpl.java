package com.xuxiang.provider.service;

import com.xuxiang.dao.CategoryMapper;
import com.xuxiang.pojo.Goods_category;
import com.xuxiang.service.CategoryService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.provider.service
 * @create: 2021/6/12
 * @FileName: CategoryServiceImpl
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper CategoryMapper;

    @Override
    public List<Goods_category> getCategoryListByParentId(Short parentId) {
        return null;
    }

    @Override
    public List<Goods_category> getAllCategoryList() {

        return CategoryMapper.selectAll();
    }


    @Override
    @Transactional
    public String delGoodsCategoryById(Short id) {
        int i = CategoryMapper.delGoodsCategoryById(id);
        System.out.println("执行删除");
        return i>0?"删除成功":"删除失败";
    }

    @Override
    @Transactional
    public String insertCategory(Goods_category category) {
        int i = CategoryMapper.insertCategory(category);
        System.out.println("执行新增");
        return i>0?"插入成功":"插入失败";
    }


    @Override
    public Goods_category getCategory(Short id) {
        return null;
    }
}
