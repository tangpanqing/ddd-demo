package com.example.test_ddd.domain.user;

import com.example.test_ddd.entity.UserEntity;
import lombok.Data;

@Data
public class UserAggregation {
    private Long userId;
    private UserEntity userEntity;

    public void consume(Integer money) {
        getUserEntity().setBalance(getUserEntity().getBalance() - money);
    }
}