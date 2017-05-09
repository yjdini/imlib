package com.ini.service.implement;

import com.ini.dao.entity.Orders;
import com.ini.service.OrderService;
import com.ini.service.SkillService;
import com.ini.service.UserService;
import com.utils.ConstJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class OrderServiceImpl implements OrderService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SkillService skillService;
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public ConstJson.Result addOrder(Orders order) {
        try {
            skillService.increaseOrderTimes(order.getSkillId());
            userService.increaseOrderTimes(order.getToUserId());
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK.setResult(order.getOrderId().toString());
    }


    @Override
    @Transactional
    public ConstJson.Result cancleOrder(Integer orderId, Integer userId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);

            if(!orderFrom(order, userId)) {//没有权限
                return ConstJson.ERROR;
            }

            order.setResult(4);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK;
    }

    @Override
    public List<Orders> getOrdersByUserId(Integer userId) {
        return entityManager.createQuery(
                "from Orders where (fromUserId = :userId or toUserId = :userId) and status = 1", Orders.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Orders getOrderDetail(Integer orderId, Integer userId) {
        Orders order = entityManager.find(Orders.class, orderId);
        if (!orderBelongs(order, userId)) {//没有权限
           return null;
        } else {
            return order;
        }
    }

    @Override
    public ConstJson.Result rejectOrder(Integer orderId, Integer userId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);

            if(!orderTo(order, userId)) {//没有权限
                return ConstJson.ERROR;
            }

            order.setResult(2);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK;
    }

    @Override
    public ConstJson.Result deleteOrder(Integer orderId, Integer userId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);

            if(order.getResult() == 0) {//待审核的预约不能删除
                return ConstJson.ERROR;
            }

            if(orderFrom(order, userId)) {
                order.setFromStatus(0);
            } else if (orderTo(order, userId)) {
                order.setToStatus(0);
            } else {
                return ConstJson.ERROR;
            }
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK;
    }

    @Override
    public ConstJson.Result finishOrder(Integer orderId, Integer userId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);
            if(!orderBelongs(order, userId)) {//没有权限
                return ConstJson.ERROR;
            }

            skillService.increaseOrderedTimes(order.getSkillId());
            userService.increaseOrderedTimes(order.getToUserId());

            order.setResult(3);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK;
    }

    @Override
    public void rejectAllOrders(Integer skillId) {
        List<Orders> orderList = entityManager.createQuery("from Orders where skillId = :skillId and result = 0", Orders.class)
                .setParameter("skillId", skillId).getResultList();
        for (Orders order : orderList) {
            order.setResult(2);
            entityManager.persist(order);
        }
    }

    private boolean orderFrom(Orders order, Integer userId) {
        return order.getFromUserId().equals(userId);
    }

    private boolean orderTo(Orders order, Integer userId) {
        return order.getToUserId().equals(userId);
    }
    private boolean orderBelongs(Orders order, Integer userId) {
        return order.getFromUserId().equals(userId) || order.getToUserId().equals(userId);
    }

}
