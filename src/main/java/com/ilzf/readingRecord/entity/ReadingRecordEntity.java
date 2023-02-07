package com.ilzf.readingRecord.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ilzf.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 阅读记录
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("reading_record")
public class ReadingRecordEntity extends BaseEntity {
    @TableId("id")
    private Integer id;
    @TableField("user_id")
    private String userId;
    @TableField("book_id")
    private String bookId;
    //章节信息
    @TableField("chapter_info")
    private String chapterInfo;
    //读到的页数
    @TableField("page_current")
    private String pageCurrent;
    //阅读日期
    @TableField("read_time")
    private Timestamp readTime;
}
