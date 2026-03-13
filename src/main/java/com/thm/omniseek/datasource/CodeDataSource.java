package com.thm.omniseek.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Code;
import com.thm.omniseek.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 代码服务实现
 *
 */
@Service
@Slf4j
public class CodeDataSource implements DataSource<Code> {

    @Resource
    private CodeService codeService;

    @Override
    public Page<Code> doSearch(String searchText, long pageNum, long pageSize) {
        SearchRequest query = new SearchRequest();
        query.setKeyword(searchText);
        query.setCurrent(pageNum);
        query.setPageSize(pageSize);
        return codeService.listCodeVOByPage(query);
    }
}
