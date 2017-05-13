package com.ini.service;

import com.ini.data.entity.Orders;
import com.ini.data.jpa.OrdersRepository;
import com.ini.service.abstrac.CommentService;
import com.ini.service.abstrac.SkillService;
import com.ini.service.abstrac.UserService;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 *
 */
public  class CommentServiceImpl implements CommentService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired private SessionUtil sessionUtil;
    @Autowired private OrdersRepository ordersRepository;
    @Autowired private SkillService skillService;
    @Autowired private UserService userService;

    @Override
    public ResultMap getCommentsBySkillId(Integer skillId) {
        List comments =  entityManager.createQuery("select new com.ini.data.schema.CommentUserSet(o,u) from " +
                " Orders o, User u where o.userId = u.userId and u.status =1 and o.isCommented = 1 " +
                " and o.skillId = :skillId")
                .setParameter("skillId", skillId)
                .getResultList();
        return ResultMap.ok().put("result", comments);
    }

    @Transactional
    @Override
    public ResultMap addComment(Integer orderId, Integer score, String content) {
        Orders order = ordersRepository.findOne(orderId);
        Integer userId = sessionUtil.getUserId();
        if (!order.getFromUserId().equals(userId)) {
            return ResultMap.error().setMessage("你不是预约的发起者，无法评论！");
        }
        if (!order.getResult().equals(3)) {
            return ResultMap.error().setMessage("该预约还未完成，无法评论！");
        }
        if (order.getIsCommented().equals(1)) {
            return ResultMap.error().setMessage("该预约已经评论过了！");
        }

        order.setIsCommented(1);
        order.setComment(content);
        order.setScore(score);
        order.setCommentTime(new Date());
        ordersRepository.save(order);

        skillService.addScore(order.getSkillId(), score);
        userService.addScore(userId, score);

        return null;
    }
}
