package com.example.test_ddd.infra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("goods")
public class GoodsEntity {

    @TableId
    private Long goodsId;
    private String goodsName;
}