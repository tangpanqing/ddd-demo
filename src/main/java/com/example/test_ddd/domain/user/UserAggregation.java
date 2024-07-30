package com.example.test_ddd.domain.user;

import com.example.test_ddd.domain.EventUtil;
import com.example.test_ddd.infra.entity.UserEntity;
import lombok.Data;

@Data
public class UserAggregation {
    private Long userId;
    private UserEntity userEntity;

    public void consume(Integer money) {
        getUserEntity().setBalance(getUserEntity().getBalance() - money);
    }

    public void changeName(String name){
        if(!getUserEntity().getNickname().equals(name)){
            getUserEntity().setNickname(name);
            EventUtil.onUsernameChange(name);
        }
    }
}