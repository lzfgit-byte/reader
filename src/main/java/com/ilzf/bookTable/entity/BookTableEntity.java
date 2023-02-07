package com.ilzf.bookTable.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ilzf.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("book_table")
public class BookTableEntity extends BaseEntity {
    @TableId("id")
    private Integer id;
    @TableField("user_id")
    private String userId;
    @TableField("book_id")
    private String bookId;
}
