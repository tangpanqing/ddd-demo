package com.example.test_ddd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test_ddd.entity.OrderCommentEntity;
import com.example.test_ddd.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderCommentMapper extends BaseMapper<OrderCommentEntity> {

}
