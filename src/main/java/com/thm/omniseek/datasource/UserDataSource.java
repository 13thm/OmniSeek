package com.thm.omniseek.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.User;
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
public class UserDataSource implements DataSource<User> {

    @Resource
    private UserService userService;

    @Override
    public Page<User> doSearch(String searchText, long pageNum, long pageSize) {
        SearchRequest userQuery = new SearchRequest();
        userQuery.setKeyword(searchText);
        userQuery.setCurrent(pageNum);
        userQuery.setPageSize(pageSize);
        Page<User> userVOPage = userService.listUserVOByPage(userQuery);
        return userVOPage;
    }
}
