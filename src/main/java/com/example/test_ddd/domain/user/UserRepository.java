package com.example.test_ddd.domain.user;

import com.example.test_ddd.domain.order.OrderAggregation;
import com.example.test_ddd.entity.UserEntity;
import com.example.test_ddd.mapper.UserMapper;
import com.example.test_ddd.utils.CompareUtil;
import com.example.test_ddd.utils.SnapshotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {
    @Autowired
    UserMapper userMapper;

    public UserAggregation find(Long userId) {
        UserAggregation oldOrder = new UserAggregation();
        oldOrder.setUserId(userId);
        oldOrder.setUserEntity(userMapper.selectById(userId));

        SnapshotUtil.putObject(oldOrder);
        return oldOrder;
    }

    public void save(UserAggregation userAggregation) {
        //实例化
        CompareUtil compareUtil = new CompareUtil();

        //回调函数
        compareUtil.regCallback(UserEntity.class, userMapper);

        //比较
        compareUtil.compare(SnapshotUtil.getObject(userAggregation), userAggregation);

        //执行
        compareUtil.run();
    }
}
