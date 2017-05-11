package com.ini.service.implement;

import com.ini.dao.entity.Orders;
import com.ini.service.OrderService;
import com.ini.service.SkillService;
import com.ini.service.UserService;
import com.utils.ConstJson;
import com.utils.ResultMap;
import com.utils.SessionUtil;
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
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SkillService skillService;
    @Autowired
    UserService userService;
    @Autowired
    SessionUtil sessionUtil;

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

    @Override
    public ResultMap rejectOrder(Integer orderId) {
        try {
            Orders order = entityManager.find(Orders.class, orderId);

            if(!orderTo(order, sessionUtil.getUserId())) {//没有权限
                return ResultMap.error().setMessage("no authority");
            }

            order.setResult(2);
            entityManager.persist(order);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok();
    }



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
            if(!orderBelongs(order, sessionUtil.getUserId())) {//没有权限
                return ResultMap.error().setMessage("no authority");
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
    public void rejectAllOrders(Integer skillId) {
        List<Orders> orderList = entityManager.createQuery("from Orders where skillId = :skillId and result = 0", Orders.class)
                .setParameter("skillId", skillId).getResultList();
        for (Orders order : orderList) {
            order.setResult(2);
            entityManager.persist(order);
        }
    }

    @Override
    public ResultMap getFromOrders() {
        List orders = entityManager.createQuery("select from new com.ini.dao.schema.OrderUser(o,to,from,t,s)"+
                " from Orders o,User to,User from, Tag t,Skill s where o.fromStatus = 1 and " +
                "from.userId = :userId and to.userId = o.toUserId and from.userId = o.fromUserId and " +
                "t.tagId = s.tagId and s.skillId = o.skillId order by o.createTime desc")
                .setParameter("userId", sessionUtil.getUserId())
                .getResultList();
        return ResultMap.ok().put("result", orders);
    }

    @Override
    public ResultMap getToOrders() {
        List orders = entityManager.createQuery("select from new com.ini.dao.schema.OrderUser(o,to,from,t,s)"+
                " from Orders o,User to,User from, Tag t,Skill s where o.toStatus = 1 and " +
                "to.userId = :userId and to.userId = o.toUserId and from.userId = o.fromUserId and " +
                "t.tagId = s.tagId and s.skillId = o.skillId order by o.createTime desc")
                .setParameter("userId", sessionUtil.getUserId())
                .getResultList();
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
