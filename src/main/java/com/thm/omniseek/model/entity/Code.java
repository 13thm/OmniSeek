package com.thm.omniseek.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("code_table")
public class Code  {
    
    @TableId(type = IdType.AUTO)
    private Integer codeId;
    
    private Integer userId;
    
    private String codeContent;
    
    private String codeLang;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
    
    @TableLogic
    @TableField("is_deleted")
    private Integer isDelete;
}