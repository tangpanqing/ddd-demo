package com.example.test_ddd.domain;

import javax.annotation.Resource;

@Resource
public class EventUtil {

    //只建议在事件里通知其他业务服务，而不要操作当前领域层
    public static void onUsernameChange(String name) {
        System.out.println("User name changed");
    }
}
