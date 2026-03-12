package com.thm.omniseek.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Photo;



public interface PhotoService extends IService<Photo> {
    Page<Photo> listPhotoVOByPage(SearchRequest query);

    QueryWrapper<Photo> getQueryWrapper(SearchRequest query);
}