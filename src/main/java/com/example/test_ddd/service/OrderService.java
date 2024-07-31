package com.example.test_ddd.service;

import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.domain.order.OrderRepository;
import com.example.test_ddd.domain.user.UserAggregation;
import com.example.test_ddd.domain.user.UserRepository;
import com.example.test_ddd.infra.entity.OrderCommentEntity;
import com.example.test_ddd.infra.entity.OrderEntity;
import com.example.test_ddd.infra.mapper.OrderCommentMapper;
import com.example.test_ddd.infra.mapper.OrderMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderCommentMapper orderCommentMapper;

    @Resource
    OrderRepository orderRepository;

    @Resource
    UserRepository userRepository;

    public List<OrderEntity> query() {
        return orderMapper.selectList(null);
    }

    public List<OrderCommentEntity> queryComment() {
        return orderCommentMapper.selectList(null);
    }

    public OrderAggregation pay(Long orderId, String payCode) {
        OrderAggregation orderAgg = orderRepository.take(orderId);
        UserAggregation userAgg = userRepository.take(orderAgg.getOrderBasic().getUserId());

        orderAgg.pay(payCode);
        userAgg.consume(orderAgg.getOrderBasic().getTotalMoney());

        orderRepository.put(orderAgg);
        userRepository.put(userAgg);

        return orderAgg;
    }
}
