package com.example.test_ddd.infra;


import com.example.test_ddd.domain.EventBus;
import org.springframework.stereotype.Component;

@Component
public class EventBusImpl implements EventBus {
    @Override
    public void onUsernameChange(String name) {

    }
}
