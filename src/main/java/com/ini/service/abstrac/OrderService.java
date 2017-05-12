package com.ini.service.abstrac;

import com.ini.dao.entity.Orders;
import com.utils.ResultMap;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface OrderService {

    ResultMap addOrder(Orders order);

    ResultMap cancleOrder(Integer orderId);

    ResultMap getOrdersByUserId();

    ResultMap getOrderDetail(Integer orderId);

    ResultMap rejectOrder(Integer orderId, String rejectReason);

    ResultMap deleteOrder(Integer orderId);

    ResultMap finishOrder(Integer orderId);

    void rejectAllOrdersOfSkill(Integer skillId);

    ResultMap getFromOrders();

    ResultMap getToOrders();

    ResultMap agreeOrder(Integer orderId);
}
