package com.thm.omniseek.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Photo;
import com.thm.omniseek.model.entity.User;
import com.thm.omniseek.service.PhotoService;
import com.thm.omniseek.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
 *
 */
@Service
@Slf4j
public class PhotoDataSource implements DataSource<Photo> {

    @Resource
    private PhotoService photoService;

    @Override
    public Page<Photo> doSearch(String searchText, long pageNum, long pageSize) {
        SearchRequest query = new SearchRequest();
        query.setKeyword(searchText);
        query.setCurrent(pageNum);
        query.setPageSize(pageSize);
        Page<Photo> photoVOPage = photoService.listPhotoVOByPage(query);
        return photoVOPage;
    }
}
