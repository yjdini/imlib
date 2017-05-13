package com.ini.service;

import com.ini.data.entity.Orders;
import com.ini.data.jpa.OrdersRepository;
import com.ini.data.schema.OrderUserSet;
import com.ini.service.abstrac.OrderService;
import com.ini.service.abstrac.SkillService;
import com.ini.service.abstrac.UserService;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 *
 */
public class OrderServiceImpl implements OrderService {
    @PersistenceContext private EntityManager entityManager;

    @Autowired private SkillService skillService;
    @Autowired private UserService userService;
    @Autowired private SessionUtil sessionUtil;
    @Autowired private OrdersRepository ordersRepository;

    @Override
    @Transactional
    public ResultMap addOrder(Orders order) {
        try {
            order.setFromUserId(sessionUtil.getUserId());
            if (order.getFromUserId().equals(order.getToUserId())) {
                return ResultMap.error().setMessage("不能自己约自己！");
            }
            skillService.increaseOrderTimes(order.getSkillId());
            userService.increaseOrderTimes(order.getToUserId());
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok().put("result", order.getOrderId());
    }


    @Override
    @Transactional
    public ResultMap cancleOrder(Integer orderId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);
            if (!orderFrom(order, sessionUtil.getUserId())) {//没有权限
                return ResultMap.error().setMessage("no authority");
            }
            order.setResult(4);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok();
    }

    @Override
    public ResultMap getOrdersByUserId() {
        List orders = entityManager.createQuery(
                "from Orders where (fromUserId = :userId or toUserId = :userId) and status = 1", Orders.class)
                .setParameter("userId", sessionUtil.getUserId())
                .getResultList();
        return ResultMap.ok().put("result", orders);
    }

    @Override
    public ResultMap getOrderDetail(Integer orderId) {
        Orders order = entityManager.find(Orders.class, orderId);
        if (!orderBelongs(order, sessionUtil.getUserId())) {//没有权限
           return ResultMap.error().setMessage("no authority");
        } else {
            return ResultMap.ok().put("result", order);
        }
    }

    @Transactional
    @Override
    public ResultMap rejectOrder(Integer orderId, String rejectReason) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);

            if(!orderTo(order, sessionUtil.getUserId())) {//没有权限
                return ResultMap.error().setMessage("no authority");
            }
            order.setRejectReason(rejectReason);
            order.setResult(2);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok();
    }

    @Override
    @Transactional
    public ResultMap agreeOrder(Integer orderId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);

            if(!orderTo(order, sessionUtil.getUserId())) {//没有权限
                return ResultMap.error().setMessage("no authority");
            }
            order.setResult(1);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok();
    }

    @Transactional
    @Override
    public ResultMap deleteOrder(Integer orderId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);

            if(order.getResult() == 0) {//待审核的预约不能删除
                return ResultMap.error().setMessage("待审核的预约不能删除！");
            }

            if(orderFrom(order, sessionUtil.getUserId())) {
                order.setFromStatus(0);
            } else if (orderTo(order, sessionUtil.getUserId())) {
                order.setToStatus(0);
            } else {
                return ResultMap.error().setMessage("该预约不属于你！");
            }
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok();
    }

    @Transactional
    @Override
    public ResultMap finishOrder(Integer orderId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);
            if (!orderBelongs(order, sessionUtil.getUserId())) {//没有权限
                return ResultMap.error().setMessage("no authority");
            }

            if (!order.getResult().equals(1)) {//不能完成非已同意的预约
                return ResultMap.error().setMessage("不能完成非同意状态的预约");
            }

            skillService.increaseOrderedTimes(order.getSkillId());
            userService.increaseOrderedTimes(order.getToUserId());

            order.setResult(3);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok();
    }

    @Override
    @Transactional
    public void rejectAllOrdersOfSkill(Integer skillId) {
        List<Orders> orderList = entityManager.createQuery("from Orders where skillId = :skillId and result = 0", Orders.class)
                .setParameter("skillId", skillId).getResultList();
        for (Orders order : orderList) {
            order.setRejectReason("该行家删除了这个技能");
            order.setResult(2);
            entityManager.persist(order);
        }
    }

    @Override
    public ResultMap getFromOrders() {
        List orders = ordersRepository.getExistFromOrders(sessionUtil.getUserId());
        return ResultMap.ok().put("result", orders);
    }


    @Override
    public ResultMap getToOrders() {
        List orders = ordersRepository.getExistToOrders(sessionUtil.getUserId());
        return ResultMap.ok().put("result", orders);
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
