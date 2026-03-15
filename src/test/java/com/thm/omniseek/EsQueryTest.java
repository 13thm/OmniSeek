package com.thm.omniseek;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * ES 数据查询测试类（基于 Java 客户端 RestHighLevelClient）
 */
@SpringBootTest
public class EsQueryTest {

    // 注入你配置好的 ES 客户端（复用 ElasticsearchConfig 中的 Bean）
    @Autowired
    private RestHighLevelClient elasticsearchClient;

    // 你的 ES 索引名（和同步时的 index 一致）
    private static final String NOVEL_INDEX_NAME = "novel_index";

    /**
     * 测试1：按文档 ID 精准查询（对应数据库 Novel 表的 id）
     * 场景：已知数据库 id，查 ES 中对应的 description
     */
    @Test
    public void testQueryById() throws IOException {
        // 1. 构建查询请求：指定索引 + 文档 ID（比如查数据库 id=1 的数据）
        GetRequest getRequest = new GetRequest(NOVEL_INDEX_NAME, "1");
        
        // 2. 执行查询
        GetResponse response = elasticsearchClient.get(getRequest, RequestOptions.DEFAULT);

        // 3. 解析结果
        if (response.isExists()) {
            System.out.println("=== 按 ID 查询结果 ===");
            System.out.println("文档 ID：" + response.getId());
            System.out.println("description 内容：" + response.getSourceAsMap().get("description"));
        } else {
            System.out.println("未找到 ID=1 的文档");
        }
    }

    /**
     * 测试2：全文检索（按 description 分词匹配）
     * 场景：搜索 description 中包含指定关键词的所有数据（ES 核心能力）
     */
    @Test
    public void testFullTextSearch() throws IOException {
        // 1. 构建搜索请求
        SearchRequest searchRequest = new SearchRequest(NOVEL_INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 2. 构建查询条件：匹配 description 中包含“科幻”的文档
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("description", "少年");
        sourceBuilder.query(matchQuery);
        sourceBuilder.size(100); // 返回最多100条（默认10条）
        searchRequest.source(sourceBuilder);
        // 3. 执行搜索
        SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        // 4. 解析结果
        System.out.println("=== 全文检索结果（关键词： 少年）===");
        System.out.println("匹配到的总条数：" + response.getHits().getTotalHits().value);
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println("文档 ID：" + hit.getId());
            System.out.println("description 内容：" + hit.getSourceAsMap().get("description"));
            System.out.println("------------------------");
        }
    }

    /**
     * 测试3：全量查询（查所有同步到 ES 的数据）
     * 场景：验证同步的数据量是否和数据库一致
     */
    @Test
    public void testQueryAll() throws IOException {
        // 1. 构建搜索请求
        SearchRequest searchRequest = new SearchRequest(NOVEL_INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 2. 构建查询条件：匹配所有文档
        MatchAllQueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
        sourceBuilder.query(matchAllQuery);
        sourceBuilder.size(1000); // 返回最多1000条（按需调整）
        searchRequest.source(sourceBuilder);

        // 3. 执行搜索
        SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

        // 4. 解析结果
        System.out.println("=== 全量查询结果 ===");
        System.out.println("ES 中总文档数：" + response.getHits().getTotalHits().value);
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println("文档 ID：" + hit.getId() + "，description：" + hit.getSourceAsMap().get("description"));
        }
    }
}