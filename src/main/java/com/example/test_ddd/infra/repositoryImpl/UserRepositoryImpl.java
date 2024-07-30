package com.example.test_ddd.infra.repositoryImpl;

import com.example.test_ddd.domain.user.UserAggregation;
import com.example.test_ddd.domain.user.UserRepository;
import com.example.test_ddd.infra.entity.UserEntity;
import com.example.test_ddd.infra.mapper.UserMapper;
import com.example.test_ddd.infra.utils.CompareUtil;
import com.example.test_ddd.infra.utils.SnapshotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    UserMapper userMapper;

    public UserAggregation take(Long userId) {
        UserAggregation oldOrder = new UserAggregation();
        oldOrder.setUserId(userId);
        oldOrder.setUserEntity(userMapper.selectById(userId));

        SnapshotUtil.putObject(oldOrder);
        return oldOrder;
    }

    public void put(UserAggregation userAggregation) {
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
