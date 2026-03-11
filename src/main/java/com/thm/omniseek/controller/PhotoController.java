package com.thm.omniseek.controller;

import com.thm.omniseek.common.BaseResponse;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.model.entity.Photo;
import com.thm.omniseek.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // 新增照片
    @PostMapping("/add")
    public BaseResponse<Boolean> addPhoto(@RequestBody Photo photo) {
        boolean save = photoService.save(photo);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), save, "添加成功");
    }

    // 删除照片（逻辑删除）
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deletePhoto(@PathVariable Integer id) {
        boolean remove = photoService.removeById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), remove, "删除成功");
    }

    // 更新照片
    @PutMapping("/update")
    public BaseResponse<Boolean> updatePhoto(@RequestBody Photo photo) {
        boolean update = photoService.updateById(photo);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), update, "更新成功");
    }

    // 根据ID查询照片
    @GetMapping("/get/{id}")
    public BaseResponse<Photo> getPhotoById(@PathVariable Integer id) {
        Photo photo = photoService.getById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), photo, "查询成功");
    }

    // 查询所有照片
    @GetMapping("/list")
    public BaseResponse<List<Photo>> listPhotos() {
        List<Photo> list = photoService.list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }

    // 根据用户ID查询照片列表
    @GetMapping("/listByUser/{userId}")
    public BaseResponse<List<Photo>> listPhotosByUser(@PathVariable Integer userId) {
        List<Photo> list = photoService.lambdaQuery().eq(Photo::getUserId, userId).list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }
}