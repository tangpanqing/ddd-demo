package com.example.test_ddd.utils;

import com.example.test_ddd.domain.goods.GoodsAggregation;
import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.entity.GoodsEntity;
import com.example.test_ddd.entity.GoodsSpecEntity;
import com.example.test_ddd.entity.OrderEntity;
import com.example.test_ddd.enums.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

public class FactoryUtil {

    public static GoodsAggregation genGoods(String goodsName){
        Long goodsId = SnowflakeUtil.genId();

        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setGoodsId(goodsId);
        goodsEntity.setGoodsName(goodsName);

        List<GoodsSpecEntity> arr = new ArrayList<>();

        GoodsAggregation oldOrder = new GoodsAggregation();
        oldOrder.setGoodsId(goodsId);
        oldOrder.setGoodsEntity(goodsEntity);
        oldOrder.setGoodsSpecList(arr);

        return oldOrder;
    }


    public static OrderAggregation genOrder(Long userId){
        Long orderId = SnowflakeUtil.genId();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setUserId(userId);
        orderEntity.setStatus(OrderStatusEnum.INIT);
        orderEntity.setTotalMoney(1000);

        OrderAggregation orderAggregation = new OrderAggregation();
        orderAggregation.setOrderId(orderId);
        orderAggregation.setOrderBasic(orderEntity);

        return orderAggregation;
    }

}
