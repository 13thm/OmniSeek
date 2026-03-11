package com.thm.omniseek.controller;

import com.thm.omniseek.common.BaseResponse;
import com.thm.omniseek.common.ErrorCode;
import com.thm.omniseek.model.entity.User;
import com.thm.omniseek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 新增用户
    @PostMapping("/add")
    public BaseResponse<Boolean> addUser(@RequestBody User user) {
        boolean save = userService.save(user);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), save, "添加成功");
    }

    // 删除用户（逻辑删除）
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteUser(@PathVariable Integer id) {
        boolean remove = userService.removeById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), remove, "删除成功");
    }

    // 更新用户
    @PutMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody User user) {
        boolean update = userService.updateById(user);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), update, "更新成功");
    }

    // 根据ID查询用户
    @GetMapping("/get/{id}")
    public BaseResponse<User> getUserById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), user, "查询成功");
    }

    // 查询所有用户
    @GetMapping("/list")
    public BaseResponse<List<User>> listUsers() {
        List<User> list = userService.list();
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), list, "查询成功");
    }
}