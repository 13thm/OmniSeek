package com.thm.omniseek.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.common.BaseResponse;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.common.ResultUtils;
import com.thm.omniseek.exception.ThrowUtils;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Novel;
import com.thm.omniseek.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小说控制器
 *
 * @author yourname
 */
@RestController
@RequestMapping("/novel")
public class NovelController {

    @Autowired
    private NovelService novelService;

    // 新增小说
    @PostMapping("/add")
    public BaseResponse<Boolean> addNovel(@RequestBody Novel novel) {
        ThrowUtils.throwIf(novel == null || novel.getNovelName() == null, ErrorCode.PARAMS_ERROR);
        boolean save = novelService.save(novel);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), save, "添加成功");
    }

    // 删除小说（逻辑删除）
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteNovel(@PathVariable Integer id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        boolean remove = novelService.removeById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), remove, "删除成功");
    }

    // 更新小说
    @PutMapping("/update")
    public BaseResponse<Boolean> updateNovel(@RequestBody Novel novel) {
        ThrowUtils.throwIf(novel == null || novel.getId() == null, ErrorCode.PARAMS_ERROR);
        boolean update = novelService.updateById(novel);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), update, "更新成功");
    }

    // 根据ID查询小说
    @GetMapping("/get/{id}")
    public BaseResponse<Novel> getNovelById(@PathVariable Integer id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        Novel novel = novelService.getById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), novel, "查询成功");
    }

    // 查询所有小说（排除已删除）
    @GetMapping("/list")
    public BaseResponse<List<Novel>> listNovels() {
        List<Novel> list = novelService.lambdaQuery().list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }

    // 根据小说名称模糊查询
    @GetMapping("/listByName/{novelName}")
    public BaseResponse<List<Novel>> listNovelsByName(@PathVariable String novelName) {
        ThrowUtils.throwIf(novelName == null || novelName.isEmpty(), ErrorCode.PARAMS_ERROR);
        List<Novel> list = novelService.lambdaQuery()
                .like(Novel::getNovelName, novelName)
                .list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }

    // 分页查询小说（支持搜索）
    @PostMapping("/list/page")
    public BaseResponse<Page<Novel>> listNovelByPage(@RequestBody SearchRequest searchRequest) {
        Page<Novel> novelPage = novelService.listNovelVOByPage(searchRequest);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), novelPage, "分页查询成功");
    }
}