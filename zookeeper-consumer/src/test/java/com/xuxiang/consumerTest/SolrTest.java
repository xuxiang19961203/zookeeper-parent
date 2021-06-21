package com.xuxiang.consumerTest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author: Xux
 * @package: com.xuxiang.consumerTest
 * @create: 2021/6/8
 * @FileName: OtherTest
 * @Description:
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SolrTest {

    private static final Logger logger = LoggerFactory.getLogger(SolrTest.class);

    @Autowired
    private SolrClient SolrClient;

    @Test
    public void testAddSolrClient() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", "1002");
        document.setField("name", "name2测试");
        document.setField("mobile_name", "mobile_name2测试");
        SolrClient.add(document);
        //提交
        SolrClient.commit();
    }

    @Test
    public void query() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        //查询的属性必须在solr库中有定义 ，否则会报错：
        /*org.apache.solr.client.solrj.impl.HttpSolrClient$RemoteSolrException:
        Error from server at http://192.168.25.168:8080/solr/Category: undefined field 自定义字段*/
        try {
         //   solrQuery.setQuery("自定义字段:苹果手机");
        //针对多属性进行查询(注意多条件连接时，之间要有空格)
        solrQuery.setQuery("name:name测试"); // 将 name测试分词之后,再查询
        QueryResponse queryResponse = SolrClient.query(solrQuery);
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("results = " + results);
        } catch (Exception e) {
          System.out.println("字段不存在");
        }
    }

    @Test
    public void delete() {
        try {
            //根据id进行删除 精确删除
            //solrClient.deleteById("7");
            //根据查询后的结果进行删除 分词删除
            SolrClient.deleteByQuery("name:测试");
            SolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
