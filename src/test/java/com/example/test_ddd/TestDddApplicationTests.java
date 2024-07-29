package com.example.test_ddd;

import com.example.test_ddd.domain.goods.GoodsAggregation;
import com.example.test_ddd.domain.goods.GoodsRepository;
import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.domain.order.OrderRepository;

import com.example.test_ddd.domain.user.UserAggregation;
import com.example.test_ddd.domain.user.UserRepository;
import com.example.test_ddd.utils.FactoryUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class TestDddApplicationTests {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(false)
    void contextLoads() throws IllegalAccessException, ClassNotFoundException, NoSuchFieldException, InstantiationException {
//        GoodsAggregation goodsAggregation = FactoryUtil.genGoods("测试商品2");
//        goodsRepository.save(goodsAggregation);
//
//        GoodsAggregation goods = goodsRepository.find(1L);
//
//        OrderAggregation oldOrder = new OrderAggregation();
//        oldOrder.setOrderId(1L);
//        oldOrder.addItem(1L, 1, goods.getGoodsSpecList().get(0).getGoodsSpecPrice());
////
////        orderRepository.save(oldOrder);

        Integer money = 100;

        UserAggregation userFrom = userRepository.find(1L);
        UserAggregation userTo = userRepository.find(2L);
    }
}
