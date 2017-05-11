package com.ini.service;

import com.ini.dao.entity.Orders;
import com.utils.ConstJson;
import com.utils.ResultMap;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface OrderService {

    ResultMap addOrder(Orders order);

    ResultMap cancleOrder(Integer orderId);

    ResultMap getOrdersByUserId();

    ResultMap getOrderDetail(Integer orderId);

    ResultMap rejectOrder(Integer orderId);

    ResultMap deleteOrder(Integer orderId);

    ResultMap finishOrder(Integer orderId);

    void rejectAllOrders(Integer skillId);

    ResultMap getFromOrders();

    ResultMap getToOrders();
}
