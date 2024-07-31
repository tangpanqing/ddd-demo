package com.example.test_ddd.domain;

import com.example.test_ddd.domain.order.OrderAggregation;

public interface Factory {

    OrderAggregation genOrder(Long userId);

}
