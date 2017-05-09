package com.ini.service;

import com.ini.entity.Orders;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface OrderService {

    ConstJson.Result addOrder(Orders order);

    ConstJson.Result cancleOrder(Integer orderId, Integer userId);

    List<Orders> getOrdersByUserId(Integer userId);

    Orders getOrderDetail(Integer orderId, Integer userId);

    ConstJson.Result rejectOrder(Integer orderId, Integer userId);

    ConstJson.Result deleteOrder(Integer orderId, Integer userId);

    ConstJson.Result finishOrder(Integer orderId, Integer userId);

    void rejectAllOrders(Integer skillId);
}
