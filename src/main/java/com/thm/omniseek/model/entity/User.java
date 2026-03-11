package com.thm.omniseek.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_table")
public class User {
    
    @TableId(type = IdType.AUTO)
    private Integer userId;
    
    private String userName;
    
    private String userPhone;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    @TableLogic
    @TableField("is_deleted")
    private Integer isDelete;
}