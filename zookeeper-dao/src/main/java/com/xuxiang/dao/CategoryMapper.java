package com.xuxiang.dao;

import com.xuxiang.pojo.Goods_category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Xux
 * @package: com.xuxiang.dao
 * @create: 2021/6/12
 * @FileName: CategoryMapper
 * @Description:
 */

public interface CategoryMapper {


    List<Goods_category> selectAll();

    List<Goods_category> getCategoryList(@Param("parent_id") Short parentId );

    Goods_category getCategoryById(@Param("id") Short id);

    int delGoodsCategoryById(@Param("id") Short id);

    int insertCategory(Goods_category category);


}