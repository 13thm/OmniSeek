package com.thm.omniseek.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thm.omniseek.mapper.UserMapper;
import com.thm.omniseek.model.entity.User;
import com.thm.omniseek.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}