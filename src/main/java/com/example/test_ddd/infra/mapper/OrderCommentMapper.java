package com.example.test_ddd.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test_ddd.infra.entity.OrderCommentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderCommentMapper extends BaseMapper<OrderCommentEntity> {

}
