package com.example.test_ddd.infra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserEntity {
    @TableId
    private Long userId;
    private String nickname;
    private Integer balance;
}