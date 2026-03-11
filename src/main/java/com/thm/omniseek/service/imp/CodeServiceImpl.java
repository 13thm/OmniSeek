package com.thm.omniseek.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thm.omniseek.mapper.CodeMapper;
import com.thm.omniseek.model.entity.Code;
import com.thm.omniseek.service.CodeService;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {
}