package com.thm.omniseek.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.model.entity.Code;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class CodeDataSourceTest {
    @Resource
    private CodeDataSource codeDataSource;
    @Test
    void test(){
        Page<Code> codePage = codeDataSource.doSearch("", 1, 10);
        System.out.println(codePage);
    }

}