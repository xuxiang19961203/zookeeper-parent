package com.xuxiang.consumerTest;

import com.xuxiang.pojo.Goods_category;
import com.xuxiang.service.CategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Xux
 * @package: com.xuxiang.consumerTest
 * @create: 2021/6/8
 * @FileName: OtherTest
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Category_SolrTest {

    private static final Logger logger = LoggerFactory.getLogger(Category_SolrTest.class);

    @Reference
    private CategoryService CategoryService;

    @Autowired
    private SolrClient SolrClient;

    @Test
    public void testSelectAll() {
        List<Goods_category> allCategoryList = CategoryService.getAllCategoryList();
        System.out.println("allCategoryList = " + allCategoryList);
    }

    //将数据库中所有category存入solr中
    @Test
    public void queryAndSave() {
        List<Goods_category> allCategoryList = CategoryService.getAllCategoryList();
        //创建solr存储对象
        for (Goods_category goods_category : allCategoryList) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", goods_category.getId());
            document.setField("name", goods_category.getName());
            document.setField("mobileName", goods_category.getMobileName());
            document.setField("parentId", goods_category.getParentId());
            document.setField("parentIdPath", goods_category.getParentIdPath());
            document.setField("level", goods_category.getLevel());
            document.setField("sortOrder", goods_category.getSortOrder());
            document.setField("isShow", goods_category.getIsShow());
            document.setField("image", goods_category.getImage());
            document.setField("isHot", goods_category.getIsHot());
            document.setField("catGroup", goods_category.getCatGroup());
            document.setField("commissionRate", goods_category.getCommissionRate());
            try {
                SolrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                logger.info("添加数据异常");
            }
            try {
                SolrClient.commit();
                logger.info("提交数据成功!");
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                logger.info("提交数据异常");
            }
        }
    }

    //将数据库中所有category存入solr中
    @Test
    public void queryAndSaveByLambda() {
        List<Goods_category> allCategoryList = CategoryService.getAllCategoryList();
        List<SolrInputDocument> collect = allCategoryList.stream().map(goods_category -> {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", goods_category.getId());
            document.setField("name", goods_category.getName() + "update");
            document.setField("mobileName", goods_category.getMobileName());
            document.setField("parentId", goods_category.getParentId());
            document.setField("parentIdPath", goods_category.getParentIdPath());
            document.setField("level", goods_category.getLevel());
            document.setField("sortOrder", goods_category.getSortOrder());
            document.setField("isShow", goods_category.getIsShow());
            document.setField("image", goods_category.getImage());
            document.setField("isHot", goods_category.getIsHot());
            document.setField("catGroup", goods_category.getCatGroup());
            document.setField("commissionRate", goods_category.getCommissionRate());
            return document;
        }).collect(Collectors.toList());

        try {
            SolrClient.add(collect.iterator());
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            logger.info("添加数据异常");
        }
        try {
            SolrClient.commit();
            logger.info("提交数据成功!");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            logger.info("提交数据异常");
        }
    }
}


