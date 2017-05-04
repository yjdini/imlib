package com.ini.service;

import com.ini.entity.Order;
import com.ini.entity.Skill;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface OrderService {

    ConstJson.Result addOrder(Order order);

    ConstJson.Result editSkill(Order order, Integer sessionUid);

    ConstJson.Result cancleOrder(Integer orderId, Integer sessionUid);

    List<Order> getOrders(Integer sessionUid);

    Order getOrderDetail(Integer orderId, Integer sessionUid);
}
