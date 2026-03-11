package com.thm.omniseek.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("photo_table")
public class Photo {
    
    @TableId(type = IdType.AUTO)
    private Integer photoId;
    
    private Integer userId;
    
    private String photoUrl;
    
    private String photoName;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    @TableLogic
    @TableField("is_deleted")
    private Integer isDelete;
}