package com.thm.omniseek.service.imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.constant.EsNovelIndexName;
import com.thm.omniseek.exception.BusinessException;
import com.thm.omniseek.mapper.NovelMapper;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Novel;
import com.thm.omniseek.service.NovelService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements NovelService {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    @Override
    public Page<Novel> listNovelVOByPage(SearchRequest query)  {
        String keyword = query.getKeyword();
        long current = query.getCurrent();
        long size = query.getPageSize();
        if (ObjectUtil.isNotEmpty(keyword)){
            org.elasticsearch.action.search.SearchRequest searchRequest = new org.elasticsearch.action.
                    search.SearchRequest(EsNovelIndexName.NOVEL_INDEX_NAME.getIndex());
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("description", keyword);
            sourceBuilder.from((int) ((current - 1) * size));
            sourceBuilder.size((int) size);
            sourceBuilder.query(matchQuery);
            sourceBuilder.trackTotalHits(true);
            searchRequest.source(sourceBuilder);
            searchRequest.source(sourceBuilder);
            SearchResponse response = null;
            try {
                response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            }catch (Exception e){
                throw new BusinessException(ErrorCode.OPERATION_ERROR);
            }
            Page<Novel> novelPage = new Page<>(current, size);
            novelPage.setTotal(response.getHits().getTotalHits().value);
            List<Novel> novelList = Arrays.stream(response.getHits().getHits())
                    .map(hit -> {
                        Novel novel = new Novel();
                        novel.setId(Integer.parseInt(hit.getId())); // ES文档ID对应数据库ID
                        novel.setNovelName((String) hit.getSourceAsMap().get("novelName"));
                        novel.setDescription((String) hit.getSourceAsMap().get("description"));
                        return novel;
                    })
                    .collect(Collectors.toList());
            novelPage.setRecords(novelList);
            return novelPage;
        }else {
            // 搜索为空
            Page<Novel> page = new Page<>(current, size);
            return this.page(page);
        }

    }

    @Override
    public QueryWrapper<Novel> getQueryWrapper(SearchRequest userQueryRequest) {
        return null;
    }
}