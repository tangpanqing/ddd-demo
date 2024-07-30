package com.example.test_ddd.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test_ddd.infra.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

}
