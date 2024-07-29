package com.example.test_ddd.domain.order;

import com.example.test_ddd.entity.OrderCommentEntity;
import com.example.test_ddd.entity.OrderEntity;
import com.example.test_ddd.entity.OrderItemEntity;
import com.example.test_ddd.enums.OrderStatusEnum;
import com.example.test_ddd.utils.SnowflakeUtil;
import lombok.Data;

import java.util.List;

@Data
public class OrderAggregation {
    private Long orderId;
    private OrderEntity orderBasic;
    private List<OrderItemEntity> orderItemList;
    private List<OrderCommentEntity> orderCommentList;

//    public void addItem(Long orderItemId, Integer goodsCount, Integer goodsPrice) {
//        OrderItemEntity orderItem = new OrderItemEntity();
//        orderItem.setOrderItemId(orderItemId);
//        orderItem.setOrderId(this.getOrderId());
//        orderItem.setGoodsCount(goodsCount);
//        orderItem.setGoodsPrice(goodsPrice);
//        orderItem.setSubtotal(goodsCount*goodsPrice);
//
//        this.getOrderItemList().add(orderItem);
//        this.getOrderEntity().setGoodsCount(this.getOrderEntity().getGoodsCount() + orderItem.getGoodsCount());
//        this.getOrderEntity().setTotalMoney(this.getOrderEntity().getTotalMoney() + orderItem.getSubtotal());
//    }

    public void pay(String payCode) {
        getOrderBasic().setStatus(OrderStatusEnum.PAY);
        getOrderBasic().setPayCode(payCode);
    }

    public void deliver(String deliverCode) {
        getOrderBasic().setStatus(OrderStatusEnum.DELIVER);
        getOrderBasic().setDeliverCode(deliverCode);
    }
    public void take(String takeCode) {
        getOrderBasic().setStatus(OrderStatusEnum.TAKE);
        getOrderBasic().setTakeCode(takeCode);
    }

    public void comment(String commentContent) {
        getOrderBasic().setStatus(OrderStatusEnum.COMMENT);

        OrderCommentEntity entity = new OrderCommentEntity();
        entity.setOrderCommentId(SnowflakeUtil.genId());
        entity.setOrderId(orderId);
        entity.setCommentContent(commentContent);
        this.getOrderCommentList().add(entity);
    }

    public void cancel() {
        getOrderBasic().setStatus(OrderStatusEnum.CANCEL);
    }
}