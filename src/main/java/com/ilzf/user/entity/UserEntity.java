package com.ilzf.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ilzf.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user")
public class UserEntity extends BaseEntity {
    @TableId("id")
    private Integer id;
    @TableField("user_name")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("log_name")
    private String logName;
}
