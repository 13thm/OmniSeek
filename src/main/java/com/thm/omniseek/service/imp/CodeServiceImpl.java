package com.thm.omniseek.service.imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.exception.BusinessException;
import com.thm.omniseek.mapper.CodeMapper;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Code;
import com.thm.omniseek.service.CodeService;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {


    @Override
    public Page<Code> listCodeVOByPage(SearchRequest query) {
        long current = query.getCurrent();
        long size = query.getPageSize();
        return this.page(new Page<>(current, size), this.getQueryWrapper(query));
    }

    @Override
    public QueryWrapper<Code> getQueryWrapper(SearchRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String text = userQueryRequest.getKeyword();
        QueryWrapper<Code> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(text)){
            queryWrapper.like(ObjectUtil.isEmpty(text), "code_content", text);
            queryWrapper.like(ObjectUtil.isEmpty(text), "code_lang", text);
        }
        return queryWrapper;
    }
}