package com.example.test_ddd.infra.repositoryImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.test_ddd.domain.goods.GoodsAggregation;
import com.example.test_ddd.domain.goods.GoodsRepository;
import com.example.test_ddd.infra.entity.GoodsEntity;
import com.example.test_ddd.infra.entity.GoodsSpecEntity;
import com.example.test_ddd.infra.mapper.GoodsMapper;
import com.example.test_ddd.infra.mapper.GoodsSpecMapper;
import com.example.test_ddd.infra.utils.CompareUtil;
import com.example.test_ddd.infra.utils.SnapshotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsRepositoryImpl implements GoodsRepository {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GoodsSpecMapper goodsSpecMapper;

    public GoodsAggregation take(Long goodsId) {
        GoodsAggregation oldOrder = new GoodsAggregation();
        oldOrder.setGoodsId(goodsId);
        oldOrder.setGoodsEntity(goodsMapper.selectById(goodsId));
        oldOrder.setGoodsSpecList(goodsSpecMapper.selectList((new LambdaQueryWrapper<GoodsSpecEntity>()).eq(GoodsSpecEntity::getGoodsId, goodsId)));

        SnapshotUtil.putObject(oldOrder);
        return oldOrder;
    }

    public void put(GoodsAggregation goodsAggregation) {
        //实例化
        CompareUtil compareUtil = new CompareUtil();

        //回调函数
        compareUtil.regCallback(GoodsEntity.class, goodsMapper);
        compareUtil.regCallback(GoodsSpecEntity.class, goodsSpecMapper);

        //比较
        compareUtil.compare(SnapshotUtil.getObject(goodsAggregation), goodsAggregation);

        //执行
        compareUtil.run();
    }
}
