package com.example.test_ddd.domain.goods;

import com.example.test_ddd.domain.EventBus;
import com.example.test_ddd.infra.entity.GoodsEntity;
import com.example.test_ddd.infra.entity.GoodsSpecEntity;
import lombok.Data;

import javax.annotation.Resource;
import java.util.List;

@Data
public class GoodsAggregation {
    private Long goodsId;
    private GoodsEntity goodsEntity;
    private List<GoodsSpecEntity> goodsSpecList;

    @Resource
    EventBus eventBus;

    public void changeGoodsName(String goodsName) {
        if (!goodsEntity.getGoodsName().equals(goodsName)) {
            goodsEntity.setGoodsName(goodsName);
            eventBus.onGoodsNameChanged(goodsEntity.getGoodsId(), goodsName);
        }
    }
}