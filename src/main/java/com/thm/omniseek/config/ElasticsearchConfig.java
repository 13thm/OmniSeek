package com.thm.omniseek.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String uris;

    // 核心：创建 ES 客户端 Bean
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(uris.replace("http://", "")) // 解析地址（如 localhost:9200）
                .withConnectTimeout(5000)    // 连接超时
                .withSocketTimeout(30000 )        // 读写超时
                // 若有认证，添加以下配置
                // .withBasicAuth("elastic", "your-password")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}