package com.thm.omniseek.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thm.omniseek.common.BaseResponse;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.common.ResultUtils;
import com.thm.omniseek.exception.ThrowUtils;
import com.thm.omniseek.model.dto.SearchRequest;
import com.thm.omniseek.model.entity.Code;
import com.thm.omniseek.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    // 新增代码块
    @PostMapping("/add")
    public BaseResponse<Boolean> addCode(@RequestBody Code code) {
        boolean save = codeService.save(code);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), save, "添加成功");
    }

    // 删除代码块（逻辑删除）
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteCode(@PathVariable Integer id) {
        boolean remove = codeService.removeById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), remove, "删除成功");
    }

    // 更新代码块
    @PutMapping("/update")
    public BaseResponse<Boolean> updateCode(@RequestBody Code code) {
        boolean update = codeService.updateById(code);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), update, "更新成功");
    }

    // 根据ID查询代码块
    @GetMapping("/get/{id}")
    public BaseResponse<Code> getCodeById(@PathVariable Integer id) {
        Code code = codeService.getById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), code, "查询成功");
    }

    // 查询所有代码块
    @GetMapping("/list")
    public BaseResponse<List<Code>> listCodes() {
        List<Code> list = codeService.list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }

    // 根据用户ID查询代码块列表
    @GetMapping("/listByUser/{userId}")
    public BaseResponse<List<Code>> listCodesByUser(@PathVariable Integer userId) {
        List<Code> list = codeService.lambdaQuery().eq(Code::getUserId, userId).list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }

    // 根据编程语言查询代码块
    @GetMapping("/listByLang/{lang}")
    public BaseResponse<List<Code>> listCodesByLang(@PathVariable String lang) {
        List<Code> list = codeService.lambdaQuery().eq(Code::getCodeLang, lang).list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }
}