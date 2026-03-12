package com.thm.omniseek.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Code;
import com.thm.omniseek.model.entity.User;

public interface CodeService extends IService<Code> {
    Page<Code> listCodeVOByPage(SearchRequest query);

    QueryWrapper<Code> getQueryWrapper(SearchRequest userQueryRequest);
}