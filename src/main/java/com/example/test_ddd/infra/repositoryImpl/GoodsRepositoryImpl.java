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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GoodsRepositoryImpl implements GoodsRepository {
    @Resource
    GoodsMapper goodsMapper;

    @Resource
    GoodsSpecMapper goodsSpecMapper;

    public GoodsAggregation take(Long goodsId) {
        GoodsEntity goodsEntity = goodsMapper.selectById(goodsId);
        if (null == goodsEntity) {
            return null;
        }

        GoodsAggregation goodsAgg = new GoodsAggregation();
        goodsAgg.setGoodsId(goodsId);
        goodsAgg.setGoodsEntity(goodsEntity);
        goodsAgg.setGoodsSpecList(goodsSpecMapper.selectList((new LambdaQueryWrapper<GoodsSpecEntity>()).eq(GoodsSpecEntity::getGoodsId, goodsId)));

        SnapshotUtil.putObject(goodsAgg);
        return goodsAgg;
    }

    public void put(GoodsAggregation goodsAgg) {
        //实例化
        CompareUtil compareUtil = new CompareUtil();

        //回调函数
        compareUtil.regCallback(GoodsEntity.class, goodsMapper);
        compareUtil.regCallback(GoodsSpecEntity.class, goodsSpecMapper);

        //比较
        compareUtil.compare(SnapshotUtil.getObject(goodsAgg), goodsAgg);

        //执行
        compareUtil.run();
    }
}
