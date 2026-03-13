package com.thm.omniseek.service.imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.exception.BusinessException;
import com.thm.omniseek.mapper.PhotoMapper;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Photo;
import com.thm.omniseek.service.PhotoService;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {
    @Override
    public Page<Photo> listPhotoVOByPage(SearchRequest query) {
        long current = query.getCurrent();
        long size = query.getPageSize();
        return this.page(new Page<>(current, size), this.getQueryWrapper(query));
    }
    @Override
    public QueryWrapper<Photo> getQueryWrapper(SearchRequest query) {
        if (query == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求参数为空");
        }
        String text = query.getKeyword();
        QueryWrapper<Photo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(text)){
            queryWrapper.like(ObjectUtil.isEmpty(text), "photo_url", text);
            queryWrapper.like(ObjectUtil.isEmpty(text), "photo_name", text);
        }
        return queryWrapper;
    }
}