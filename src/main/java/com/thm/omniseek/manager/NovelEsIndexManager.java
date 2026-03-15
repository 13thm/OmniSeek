package com.thm.omniseek.manager;

import com.thm.omniseek.service.NovelService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NovelEsIndexManager {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    @Resource
    private NovelService novelService;
    /**
     * 检查索引是否存在
     */
    public boolean checkIndexExists(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return elasticsearchClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 构建索引映射（仅映射 description 字段）
     *
     */
    public void createNovelIndexMapping(String index) throws IOException {
        // 构建索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        // 定义映射[最后的放回]
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        // description 字段配置（text 类型，支持分词）
        Map<String, Object> description = new HashMap<>();
        description.put("type", "text");
        description.put("analyzer", "ik_max_word"); // 有IK分词器则用，无则改 standard
        description.put("search_analyzer", "ik_smart");
        properties.put("description", description);
        mapping.put("properties", properties);
        request.mapping(mapping); // 直接传入 Map 类型的映射
        // 执行创建索引
        elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     */
    public void deleteNovelIndex(String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        elasticsearchClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(index+"索引已删除");
    }

    public void syncDbDataToEs(String index) throws IOException {
        // 1. 从数据库查询全量 description 数据
        List<Map<String, Object>> novelDataList = novelService.listMaps();
        if (novelDataList.isEmpty()) {
            System.out.println("数据库中无可用的 Novel 数据，同步终止");
            return;
        }
        //  构建 Bulk 请求批量写入 ES
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> data : novelDataList) {
            // 获取数据库中的 id（作为 ES 文档 ID，保证唯一性）
            Integer id = (Integer) data.get("id");
            String description = (String) data.get("description");
            String novelName = (String) data.get("novel_name");
            Map<String, Object> esDoc = new HashMap<>();
            // 构建 ES 文档
            esDoc.put("description", description);
            esDoc.put("novelName", novelName);
            IndexRequest indexRequest = new IndexRequest(index)
                    .id(id.toString()) // 文档 ID 与数据库主键一致
                    .source(esDoc, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (bulkResponse.hasFailures()) {
            System.err.println("ES 批量写入失败：" + bulkResponse.buildFailureMessage());
            throw new RuntimeException("全量同步数据库数据到 ES 失败");
        } else {
            System.out.println("成功同步 " + novelDataList.size() + " 条 Novel 数据到 ES 索引：" + index);
        }
    }
}