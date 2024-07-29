package com.example.test_ddd.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("goods_spec")
public class GoodsSpecEntity {
    @TableId
    private Long goodsSpecId;
    private Long goodsId;
    private String goodsSpecName;
    private Integer goodsSpecPrice;
}