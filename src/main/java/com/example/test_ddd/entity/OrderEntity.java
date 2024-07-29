package com.example.test_ddd.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sale_order")
public class OrderEntity {
    @TableId
    private Long orderId;
    private Long userId;
    private Integer goodsCount;
    private Integer totalMoney;

    private Integer status;
    private String payCode;
    private String deliverCode;
    private String takeCode;
}