package com.example.test_ddd.interfaces.controller;

import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.domain.order.OrderRepository;
import com.example.test_ddd.domain.user.UserRepository;
import com.example.test_ddd.infra.entity.OrderCommentEntity;
import com.example.test_ddd.infra.entity.OrderEntity;
import com.example.test_ddd.domain.Factory;
import com.example.test_ddd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrderController {

    @Resource
    Factory factory;

    @Resource
    OrderRepository orderRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    OrderService orderService;

    //注意，这里应该返回VO-ViewObject
    @RequestMapping("/order/query")
    @ResponseBody
    public List<OrderEntity> query() {
        return orderService.query();
    }

    //注意，这里应该返回VO-ViewObject
    @RequestMapping("/orderComment/query")
    @ResponseBody
    public List<OrderCommentEntity> orderCommentQuery() {
        return orderService.queryComment();
    }

    @RequestMapping("/order/detail")
    @ResponseBody
    public OrderAggregation detail(Long orderId) {
        OrderAggregation orderAgg = orderRepository.take(orderId);

        return orderAgg;
    }

    @RequestMapping("/order/init")
    @ResponseBody
    public OrderAggregation init() {
        OrderAggregation orderAgg = factory.genOrder(1L);
        orderRepository.put(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/pay")
    @ResponseBody
    public OrderAggregation pay(Long orderId, String payCode) {
        return orderService.pay(orderId, payCode);
    }

    @RequestMapping("/order/deliver")
    @ResponseBody
    public OrderAggregation deliver(Long orderId, String deliverCode) {
        OrderAggregation orderAgg = orderRepository.take(orderId);
        orderAgg.deliver(deliverCode);
        orderRepository.put(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/take")
    @ResponseBody
    public OrderAggregation take(Long orderId, String takeCode) {
        OrderAggregation orderAgg = orderRepository.take(orderId);
        orderAgg.take(takeCode);
        orderRepository.put(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/comment")
    @ResponseBody
    public OrderAggregation comment(Long orderId, String commentContent) {
        OrderAggregation orderAgg = orderRepository.take(orderId);
        orderAgg.comment(commentContent);
        orderRepository.put(orderAgg);

        return orderAgg;
    }

    @RequestMapping("/order/cancel")
    @ResponseBody
    public OrderAggregation cancel(Long orderId) {
        OrderAggregation orderAgg = orderRepository.take(orderId);
        orderAgg.cancel();
        orderRepository.put(orderAgg);

        return orderAgg;
    }
}
