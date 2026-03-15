package com.thm.omniseek.job.once;

import com.thm.omniseek.manager.NovelEsIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FullAmountSync {

    @Autowired
    private NovelEsIndexManager novelEsIndexManager;

    // 索引准备主流程：检查→删除（存在则删）→重建
    public void prepareEsIndex() {
        try {
            // 检查索引是否存在
            boolean isExists = novelEsIndexManager.checkIndexExists("novel_index");
            if (isExists) {
                // 存在则删除
                novelEsIndexManager.deleteNovelIndex("novel_index");
            }
            // 构建新索引映射
            novelEsIndexManager.createNovelIndexMapping("novel_index");
            novelEsIndexManager.syncDbDataToEs("novel_index");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ES 索引准备失败", e);
        }
    }


}