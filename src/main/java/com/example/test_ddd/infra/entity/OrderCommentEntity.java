package com.example.test_ddd.infra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sale_order_comment")
public class OrderCommentEntity {
    @TableId
    private Long orderCommentId;
    private Long orderId;
    private String commentContent;
}