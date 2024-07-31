package com.example.test_ddd.domain.user;

import com.example.test_ddd.domain.EventBus;
import com.example.test_ddd.infra.entity.UserEntity;
import lombok.Data;

import javax.annotation.Resource;

@Data
public class UserAggregation {
    private Long userId;
    private UserEntity userEntity;

    @Resource
    EventBus eventBus;

    public void consume(Integer money) {
        getUserEntity().setBalance(getUserEntity().getBalance() - money);
    }

    public void changeNickname(String nickname){
        if(!userEntity.getNickname().equals(nickname)){
            userEntity.setNickname(nickname);
            eventBus.onUserNicknameChanged(userEntity.getUserId(), nickname);
        }
    }
}