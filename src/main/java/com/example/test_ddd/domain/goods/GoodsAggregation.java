package com.example.test_ddd.domain.goods;

import com.example.test_ddd.entity.GoodsEntity;
import com.example.test_ddd.entity.GoodsSpecEntity;
import lombok.Data;

import java.util.List;

@Data
public class GoodsAggregation {
    private Long goodsId;
    private GoodsEntity goodsEntity;
    private List<GoodsSpecEntity> goodsSpecList;

    public void changeGoodsName(String goodsName) {
        this.getGoodsEntity().setGoodsName(goodsName);
    }
}