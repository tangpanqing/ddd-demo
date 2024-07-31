package com.example.test_ddd.domain;

public interface EventBus {
    void onUserNicknameChanged(Long userId, String name);

    void onGoodsNameChanged(Long goodsId, String name);
}
