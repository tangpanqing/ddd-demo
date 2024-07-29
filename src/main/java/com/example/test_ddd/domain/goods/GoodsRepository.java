package com.example.test_ddd.domain.goods;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.test_ddd.entity.GoodsEntity;
import com.example.test_ddd.entity.GoodsSpecEntity;
import com.example.test_ddd.mapper.GoodsMapper;
import com.example.test_ddd.mapper.GoodsSpecMapper;
import com.example.test_ddd.utils.CompareUtil;
import com.example.test_ddd.utils.SnapshotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsRepository {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GoodsSpecMapper goodsSpecMapper;

    public GoodsAggregation find(Long goodsId) {
        GoodsAggregation oldOrder = new GoodsAggregation();
        oldOrder.setGoodsId(goodsId);
        oldOrder.setGoodsEntity(goodsMapper.selectById(goodsId));
        oldOrder.setGoodsSpecList(goodsSpecMapper.selectList((new LambdaQueryWrapper<GoodsSpecEntity>()).eq(GoodsSpecEntity::getGoodsId, goodsId)));

        SnapshotUtil.putObject(oldOrder);
        return oldOrder;
    }

    public void save(GoodsAggregation goodsAggregation) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
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
