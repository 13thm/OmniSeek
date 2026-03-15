package com.thm.omniseek.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Novel;
import org.springframework.stereotype.Service;

@Service
public interface NovelService extends IService<Novel> {
    Page<Novel> listNovelVOByPage(SearchRequest query);
}