package com.thm.omniseek;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ElasticsearchConnectionTest {

    // 注入配置好的 ES 客户端
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testEsConnection() {
        try {
            // 构建请求：调用 ES 集群健康检查接口（/_cluster/health）
            Request request = new Request("GET", "/_cluster/health");
            // 执行请求，获取响应
            Response response = restHighLevelClient.getLowLevelClient().performRequest(request);

            // 打印响应信息，验证连接
            System.out.println("=== ES 连接成功 ===");
            System.out.println("响应状态码：" + response.getStatusLine().getStatusCode());
            System.out.println("响应头：" + response.getHeaders());
            // 若状态码为 200，说明连接正常
        } catch (IOException e) {
            System.err.println("=== ES 连接失败 ===");
            e.printStackTrace();
        }
    }
}