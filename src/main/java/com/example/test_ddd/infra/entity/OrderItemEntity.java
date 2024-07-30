package com.example.test_ddd.infra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sale_order_item")
public class OrderItemEntity {
    @TableId
    private Long orderItemId;
    private Long orderId;
    private Integer goodsCount;
    private Integer goodsPrice;
    private Integer subtotal;
}