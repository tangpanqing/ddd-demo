package com.example.test_ddd.infra.repositoryImpl;

import com.example.test_ddd.domain.user.UserAggregation;
import com.example.test_ddd.domain.user.UserRepository;
import com.example.test_ddd.infra.entity.OrderEntity;
import com.example.test_ddd.infra.entity.UserEntity;
import com.example.test_ddd.infra.mapper.UserMapper;
import com.example.test_ddd.infra.utils.CompareUtil;
import com.example.test_ddd.infra.utils.SnapshotUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Resource
    UserMapper userMapper;

    public UserAggregation take(Long userId) {
        UserEntity userEntity = userMapper.selectById(userId);
        if (null == userEntity) {
            return null;
        }

        UserAggregation userAgg = new UserAggregation();
        userAgg.setUserId(userId);
        userAgg.setUserEntity(userEntity);

        SnapshotUtil.putObject(userAgg);
        return userAgg;
    }

    public void put(UserAggregation userAgg) {
        //实例化
        CompareUtil compareUtil = new CompareUtil();

        //回调函数
        compareUtil.regCallback(UserEntity.class, userMapper);

        //比较
        compareUtil.compare(SnapshotUtil.getObject(userAgg), userAgg);

        //执行
        compareUtil.run();
    }
}
