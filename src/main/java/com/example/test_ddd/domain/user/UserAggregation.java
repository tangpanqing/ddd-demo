package com.example.test_ddd.domain.user;

import com.example.test_ddd.domain.EventBus;
import com.example.test_ddd.infra.entity.UserEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class UserAggregation {
    private Long userId;
    private UserEntity userEntity;

    @Resource
    EventBus eventBus;

    public void consume(Integer money) {
        getUserEntity().setBalance(getUserEntity().getBalance() - money);
    }

    public void changeName(String name){
        if(!getUserEntity().getNickname().equals(name)){
            getUserEntity().setNickname(name);
            eventBus.onUsernameChange(name);
        }
    }
}