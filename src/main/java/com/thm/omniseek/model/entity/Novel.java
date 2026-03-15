package com.thm.omniseek.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 小说实体类
 * 对应表：novel
 */
@Data
@TableName("novel")
public class Novel {

    /**
     * 主键ID，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 小说名称
     */
    private String novelName;

    /**
     * 小说描述（30字左右）
     */
    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDelete;
}