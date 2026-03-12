package com.thm.omniseek.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.User;

public interface UserService extends IService<User> {


    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(SearchRequest userQueryRequest);

    /**
     * 分页查询用户
     * @param userQuery
     */
    Page<User> listUserVOByPage(SearchRequest userQuery);
}