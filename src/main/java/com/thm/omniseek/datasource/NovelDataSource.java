package com.thm.omniseek.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Novel;
import com.thm.omniseek.service.NovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class NovelDataSource implements DataSource<Novel>{

    @Resource
    private NovelService novelService;
    @Override
    public Page<Novel> doSearch(String searchText, long pageNum, long pageSize) {
        SearchRequest query = new SearchRequest();
        query.setKeyword(searchText);
        query.setCurrent(pageNum);
        query.setPageSize(pageSize);
        return novelService.listNovelVOByPage(query);
    }
    // 这个是利用ES进行搜索的

}
