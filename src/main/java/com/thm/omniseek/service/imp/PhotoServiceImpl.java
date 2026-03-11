package com.thm.omniseek.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.thm.omniseek.mapper.PhotoMapper;
import com.thm.omniseek.model.entity.Photo;
import com.thm.omniseek.service.PhotoService;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {
}