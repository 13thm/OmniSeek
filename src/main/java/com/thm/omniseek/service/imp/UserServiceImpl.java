package com.thm.omniseek.service.imp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.exception.BusinessException;
import com.thm.omniseek.mapper.UserMapper;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.User;
import com.thm.omniseek.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public QueryWrapper<User> getQueryWrapper(SearchRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String text = userQueryRequest.getKeyword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(ObjectUtil.isEmpty(text),"userName",text);
        queryWrapper.like(ObjectUtil.isEmpty(text),"userPhone",text);
        return queryWrapper;
    }
    @Override
    public Page<User> listUserVOByPage(SearchRequest userQuery) {
        long current = userQuery.getCurrent();
        long size = userQuery.getPageSize();
        Page<User> userPage = this.page(new Page<>(current, size),
                this.getQueryWrapper(userQuery));
        return new Page<>(current, size, userPage.getTotal());
    }
}