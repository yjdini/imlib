package com.ini.service;

import com.utils.ConstJson;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class OrderServiceImpl implements OrderService{
    @Override
    public ConstJson.Result addOrder(com.ini.entity.Order order) {
        return null;
    }

    @Override
    public ConstJson.Result editSkill(com.ini.entity.Order order, Integer sessionUid) {
        return null;
    }

    @Override
    public ConstJson.Result cancleOrder(Integer orderId, Integer sessionUid) {
        return null;
    }

    @Override
    public List<com.ini.entity.Order> getOrders(Integer sessionUid) {
        return null;
    }

    @Override
    public com.ini.entity.Order getOrderDetail(Integer orderId, Integer sessionUid) {
        return null;
    }

    @Override
    public ConstJson.Result rejectOrder(Integer orderId, Integer sessionUid) {
        return null;
    }

    @Override
    public ConstJson.Result deleteOrder(Integer orderId, Integer sessionUid) {
        return null;
    }
}
