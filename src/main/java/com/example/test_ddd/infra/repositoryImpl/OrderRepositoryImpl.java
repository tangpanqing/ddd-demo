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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    OrderCommentMapper orderCommentMapper;

    public OrderAggregation take(Long orderId) {
        OrderAggregation orderAggregation = new OrderAggregation();
        orderAggregation.setOrderId(orderId);
        orderAggregation.setOrderBasic(orderMapper.selectById(orderId));
        orderAggregation.setOrderItemList(orderItemMapper.selectList((new LambdaQueryWrapper<OrderItemEntity>()).eq(OrderItemEntity::getOrderId, orderId)));
        orderAggregation.setOrderCommentList(orderCommentMapper.selectList((new LambdaQueryWrapper<OrderCommentEntity>()).eq(OrderCommentEntity::getOrderId, orderId)));

        SnapshotUtil.putObject(orderAggregation);
        return orderAggregation;
    }

    public void put(OrderAggregation orderAggregation) {
        //实例化
        CompareUtil compareUtil = new CompareUtil();

        //回调函数
        compareUtil.regCallback(OrderEntity.class, orderMapper);
        compareUtil.regCallback(OrderItemEntity.class, orderItemMapper);
        compareUtil.regCallback(OrderCommentEntity.class, orderCommentMapper);

        //比较
        compareUtil.compare(SnapshotUtil.getObject(orderAggregation), orderAggregation);

        //执行
        compareUtil.run();
    }

}
