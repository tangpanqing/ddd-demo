package com.example.test_ddd.infra;

import com.example.test_ddd.domain.Factory;
import com.example.test_ddd.domain.goods.GoodsAggregation;
import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.infra.entity.GoodsEntity;
import com.example.test_ddd.infra.entity.GoodsSpecEntity;
import com.example.test_ddd.infra.entity.OrderEntity;
import com.example.test_ddd.infra.enums.OrderStatusEnum;
import com.example.test_ddd.infra.utils.SnowflakeUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FactoryImpl implements Factory {

    public  GoodsAggregation genGoods(String goodsName){
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


    public  OrderAggregation genOrder(Long userId){
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
