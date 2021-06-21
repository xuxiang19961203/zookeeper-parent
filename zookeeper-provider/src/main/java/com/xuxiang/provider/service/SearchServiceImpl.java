package com.xuxiang.provider.service;

import com.xuxiang.pojo.Goods_category;
import com.xuxiang.pojo.PageResultBean;
import com.xuxiang.pojo.User;
import com.xuxiang.service.CategoryService;
import com.xuxiang.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: Xux
 * @package: com.xuxiang.provider.service
 * @create: 2021/6/13
 * @FileName: SearchServiceImpl
 * @Description:
 */
@Service
public class SearchServiceImpl implements SearchService {

    //@Autowired
    //private SolrClient SolrClient;

    @Reference
    private CategoryService CategoryService;

    private Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private CloudSolrClient SolrClient;

    @Autowired
    private User user;

    //初始化,设置默认的collection
    @Resource
    public void setSolrClient(CloudSolrClient SolrClient) {
        this.SolrClient = SolrClient;
        SolrClient.setDefaultCollection("Category");
        logger.info("默认Collection已设定:Category");
    }


    @Override
    public PageResultBean<Goods_category> queryByKeywords(String keywords, Integer pageStart, Integer pageRows) {
        SolrQuery SolrQuery = new SolrQuery();
        SolrQuery.setQuery("category_keywords:" + keywords);
        //SolrQuery.setQuery("*:*");
        //开启分页
        SolrQuery.setStart((pageStart - 1) * pageRows);
        logger.info("分页起始条数:{}", (pageStart - 1) * pageRows);
        SolrQuery.setRows(pageRows);
        logger.info("每页大小:{}", pageRows);
        SolrDocumentList documentList = null;
        try {
            QueryResponse query = SolrClient.query(SolrQuery);
            documentList = query.getResults();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            logger.error("查询失败!");
        }
        // PageInfo
        List<Goods_category> categoryList = null;
        if (documentList != null && documentList.getNumFound() > 0) {
            categoryList = documentList.stream().map(document -> {
                Goods_category category = new Goods_category();
                category.setId(Short.parseShort((String) document.getFieldValue("id")));
                category.setName((String) document.getFieldValue("name"));
                category.setMobileName((String) document.getFieldValue("mobileName"));
                category.setCatGroup((byte) (int) document.getFieldValue("catGroup"));
                category.setImage((String) document.getFieldValue("image"));
                category.setIsShow((byte) (int) document.getFieldValue(("isShow")));
                category.setSortOrder((byte) (int) document.getFieldValue(("sortOrder")));
                return category;
            }).collect(Collectors.toList());
        }
        PageResultBean pageBean = new PageResultBean();
        pageBean.setPageNum(pageStart);
        pageBean.setPageSize(pageRows);
        int total = (int) documentList.getNumFound();
        //设置总条数
        pageBean.setSize(total);
        //设置list
        pageBean.setList(categoryList);
        return pageBean;
    }


    //高亮查询
    @Override
    public PageResultBean<Goods_category> queryByKeywordsWithHighLight(String keywords, Integer pageStart, Integer pageRows) {
        SolrQuery SolrQuery = new SolrQuery();
        SolrQuery.setQuery("category_keywords:" + keywords);
        //SolrQuery.setQuery("*:*");
        //开启高亮显示
        SolrQuery.setHighlight(true);
        //给name,mobileName 字段查询到的数据 增加高亮 高亮字段为category_keywords 的复制域
        SolrQuery.addHighlightField("name");
        SolrQuery.addHighlightField("mobileName");
        //标记，高亮关键字前缀
        SolrQuery.setHighlightSimplePre("<font color='red'>");
        SolrQuery.setHighlightSimplePost("</font>");//后缀

        //开启分页
        SolrQuery.setStart((pageStart - 1) * pageRows);
        logger.info("分页起始条数:{}", (pageStart - 1) * pageRows);
        SolrQuery.setRows(pageRows);
        logger.info("每页大小:{}", pageRows);
        SolrDocumentList documentList = null;
        List<Goods_category> categoryList = null;
        try {
            QueryResponse query = SolrClient.query(SolrQuery);
            documentList = query.getResults();
            if (documentList.getNumFound() > 0) {
                categoryList = documentList.stream().map(document -> {
                    Goods_category category = new Goods_category();
                    category.setId(Short.parseShort((String) document.getFieldValue("id")));
                    //设置name属性为高亮展示
                    //category.setName((String) document.getFieldValue("name"));
                    //category.setMobileName((String) document.getFieldValue("mobileName"));
                    category.setCatGroup((byte) (int) document.getFieldValue("catGroup"));
                    category.setImage((String) document.getFieldValue("image"));
                    category.setIsShow((byte) (int) document.getFieldValue(("isShow")));
                    category.setSortOrder((byte) (int) document.getFieldValue(("sortOrder")));
                    /**获取高亮数据 第一个String为 id的值, map对应高亮的信息  k:属性  v:高亮字段*/
                    Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
                    logger.info("highlighting =" + highlighting);
                    //获取某个id的数据的高亮信息
                    //Map<String, List<String>> categoryHighLight = highlighting.get("18");
                    Map<String, List<String>> categoryHighLight = highlighting.get(document.getFieldValue("id"));
                    //获取此数据的 name属性的高亮信息
                    List<String> nameHighLight = categoryHighLight.get("name");
                    /**因为目标域存在多个复制域,因此查询到的结果,高亮文本不一定在name 属性上, 可能在其他复制域上 如 mobileName
                     * 如果 query的 属性在 其他字段上, 则当前属性的 List 为null
                     * 如: "category_keywords:" + 手机  走索引库查到  name:手机XX  mobileName:手机XX 则正常
                     * 如: "category_keywords:" + 手机  走索引库查到  name:手机XX  mobileName:XX 则mobileName高亮List为null
                     */
                    if (nameHighLight != null && nameHighLight.size() > 0) {
                        //虽然高亮数据返回的是一个集合,但是高亮字段存在第一个index的位置
                        category.setName(nameHighLight.get(0));
                    } else {
                        //没有高亮字段则 设置普通模式
                        category.setName((String) document.getFieldValue("name"));
                    }
                    //获取此数据的 mobileName属性的高亮信息
                    List<String> mobileNameHighLight = categoryHighLight.get("mobileName");
                    if (mobileNameHighLight != null && mobileNameHighLight.size() > 0) {
                        //虽然高亮数据返回的是一个集合,但是高亮字段存在第一个index的位置
                        category.setMobileName(mobileNameHighLight.get(0));
                    } else {
                        //没有高亮字段则 设置普通模式
                        category.setMobileName((String) document.getFieldValue("mobileName"));
                    }
                    return category;
                }).collect(Collectors.toList());
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            logger.error("查询失败!");
        }

        PageResultBean pageBean = new PageResultBean();
        pageBean.setPageNum(pageStart);
        pageBean.setPageSize(pageRows);
        int total = (int) documentList.getNumFound();
        //设置总条数
        pageBean.setSize(total);
        //设置list
        pageBean.setList(categoryList);
        return pageBean;
    }

    @Override
    public List<Goods_category> queryByKeywords(String keywords) {
        SolrQuery SolrQuery = new SolrQuery();
        SolrQuery.setQuery("category_keywords:" + keywords);
        SolrDocumentList documentList = null;
        try {
            QueryResponse query = SolrClient.query(SolrQuery);
            documentList = query.getResults();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            logger.error("查询失败!");
        }
        List<Goods_category> categoryList = null;
        if (documentList.size() > 0) {
            categoryList = documentList.stream().map(document -> {
                Goods_category category = new Goods_category();
                category.setId(Short.parseShort((String) document.getFieldValue("id")));
                category.setName((String) document.getFieldValue("name"));
                category.setMobileName((String) document.getFieldValue("mobileName"));
                category.setCatGroup((byte) (int) document.getFieldValue("catGroup"));
                category.setImage((String) document.getFieldValue("image"));
                category.setIsShow((byte) (int) document.getFieldValue(("isShow")));
                category.setSortOrder((byte) (int) document.getFieldValue(("sortOrder")));
                return category;
            }).collect(Collectors.toList());
        }
        return categoryList;
    }

    @Override
    public String saveKeywords(Goods_category category) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", category.getId());
        document.setField("name", category.getName());
        try {
            SolrClient.add(document);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            logger.error("添加失败!");
            return "失败!";
        }
        try {
            SolrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return "成功!";
    }

    @Override
    public String initAllToSolr() {
        logger.info("user=" + user);
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
                return "失败!";
            }
            try {
                SolrClient.commit();
                logger.info("提交数据成功!");
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                logger.info("提交数据异常");
            }
        }
        return "成功!";
    }
}
