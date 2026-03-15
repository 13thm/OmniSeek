package com.thm.omniseek.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.controller.AllSearchContoller;
import com.thm.omniseek.job.once.FullAmountSync;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Code;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class CodeDataSourceTest {
    @Resource
    private CodeDataSource codeDataSource;

    @Resource
    private AllSearchContoller allSearchContoller;

    @Resource
    private FullAmountSync fullAmountSync;
    @Test
    void test(){
        fullAmountSync.prepareEsIndex();

    }


}