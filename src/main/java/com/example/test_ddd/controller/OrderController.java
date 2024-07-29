package com.example.test_ddd.controller;

import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.domain.order.OrderRepository;
import com.example.test_ddd.domain.user.UserAggregation;
import com.example.test_ddd.domain.user.UserRepository;
import com.example.test_ddd.entity.OrderCommentEntity;
import com.example.test_ddd.entity.OrderEntity;
import com.example.test_ddd.mapper.OrderCommentMapper;
import com.example.test_ddd.mapper.OrderMapper;
import com.example.test_ddd.utils.FactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderCommentMapper orderCommentMapper;

    //注意，这里应该返回VO-ViewObject
    @RequestMapping("/order/query")
    @ResponseBody
    public List<OrderEntity> query() {
        return orderMapper.selectList(null);
    }

    //注意，这里应该返回VO-ViewObject
    @RequestMapping("/orderComment/query")
    @ResponseBody
    public List<OrderCommentEntity> orderCommentQuery() {
        return orderCommentMapper.selectList(null);
    }

    @RequestMapping("/order/detail")
    @ResponseBody
    public OrderAggregation detail(Long orderId) {
        OrderAggregation orderAgg = orderRepository.find(orderId);

        return orderAgg;
    }

    @RequestMapping("/order/init")
    @ResponseBody
    public OrderAggregation init() {
        OrderAggregation orderAgg = FactoryUtil.genOrder(1L);
        orderRepository.save(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/pay")
    @ResponseBody
    public OrderAggregation pay(Long orderId, String payCode) {
        OrderAggregation orderAgg = orderRepository.find(orderId);
        UserAggregation userAgg = userRepository.find(orderAgg.getOrderBasic().getUserId());

        orderAgg.pay(payCode);

        userAgg.consume(orderAgg.getOrderBasic().getTotalMoney());

        orderRepository.save(orderAgg);
        userRepository.save(userAgg);

        return orderAgg;
    }

    @RequestMapping("/order/deliver")
    @ResponseBody
    public OrderAggregation deliver(Long orderId, String deliverCode) {
        OrderAggregation orderAgg = orderRepository.find(orderId);
        orderAgg.deliver(deliverCode);
        orderRepository.save(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/take")
    @ResponseBody
    public OrderAggregation take(Long orderId, String takeCode) {
        OrderAggregation orderAgg = orderRepository.find(orderId);
        orderAgg.take(takeCode);
        orderRepository.save(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/comment")
    @ResponseBody
    public OrderAggregation comment(Long orderId, String commentContent) {
        OrderAggregation orderAgg = orderRepository.find(orderId);
        orderAgg.comment(commentContent);
        orderRepository.save(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/cancel")
    @ResponseBody
    public OrderAggregation cancel(Long orderId) {
        OrderAggregation orderAgg = orderRepository.find(orderId);
        orderAgg.cancel();
        orderRepository.save(orderAgg);

        return orderAgg;
    }
}
