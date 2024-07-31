package com.example.test_ddd.infra.repositoryImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.domain.order.OrderRepository;
import com.example.test_ddd.infra.entity.OrderCommentEntity;
import com.example.test_ddd.infra.entity.OrderEntity;
import com.example.test_ddd.infra.entity.OrderItemEntity;
import com.example.test_ddd.infra.mapper.OrderCommentMapper;
import com.example.test_ddd.infra.mapper.OrderItemMapper;
import com.example.test_ddd.infra.mapper.OrderMapper;
import com.example.test_ddd.infra.utils.CompareUtil;
import com.example.test_ddd.infra.utils.SnapshotUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    @Resource
    OrderCommentMapper orderCommentMapper;

    public OrderAggregation take(Long orderId) {
        OrderEntity orderBase = orderMapper.selectById(orderId);
        if (null == orderBase) {
            return null;
        }

        OrderAggregation orderAgg = new OrderAggregation();
        orderAgg.setOrderId(orderId);
        orderAgg.setOrderBasic(orderBase);
        orderAgg.setOrderItemList(orderItemMapper.selectList((new LambdaQueryWrapper<OrderItemEntity>()).eq(OrderItemEntity::getOrderId, orderId)));
        orderAgg.setOrderCommentList(orderCommentMapper.selectList((new LambdaQueryWrapper<OrderCommentEntity>()).eq(OrderCommentEntity::getOrderId, orderId)));

        SnapshotUtil.putObject(orderAgg);
        return orderAgg;
    }

    public void put(OrderAggregation orderAgg) {
        //实例化
        CompareUtil compareUtil = new CompareUtil();

        //回调函数
        compareUtil.regCallback(OrderEntity.class, orderMapper);
        compareUtil.regCallback(OrderItemEntity.class, orderItemMapper);
        compareUtil.regCallback(OrderCommentEntity.class, orderCommentMapper);

        //比较
        compareUtil.compare(SnapshotUtil.getObject(orderAgg), orderAgg);

        //执行
        compareUtil.run();
    }

}
