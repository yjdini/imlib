package com.ini.service.implement;

import com.ini.entity.Orders;
import com.ini.entity.Skill;
import com.ini.service.OrderService;
import com.ini.service.SkillService;
import com.utils.ConstJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class SkillServiceImpl implements SkillService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderService orderService;

    @Override
    @Transactional
    public ConstJson.Result addSkill(Skill skill) {
        try {
            entityManager.persist(skill);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK;
    }

    @Override
    @Transactional
    public ConstJson.Result deleteSkill(Integer skillId, Integer userId) {
        try {
            Skill skill = entityManager.find(Skill.class, skillId);
            if(!skill.getUserId().equals(userId)) {//没有权限
                return ConstJson.ERROR;
            }

            orderService.rejectAllOrders(skillId);

            skill.setStatus(0);
            entityManager.persist(skill);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK;
    }

    @Override
    public List<Skill> getSkillsByUserId(Integer userId) {
        return entityManager.createQuery("from skill where userId = :userId and status = 1", Skill.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Skill getSkillDetail(Integer skillId) {
        return entityManager.find(Skill.class, skillId);
    }

    @Override
    public List<Skill> searchByKeyword(String keyword, Integer subId) {
        Collections.singletonMap("",);
        entityManager.createNamedQuery();
        entityManager.createEntityGraph(Skill.class).;
    }

    @Override
    public List<Skill> searchByTagId(Integer tagId, Integer subId) {
        return null;
    }

    @Override
    public void increaseOrderTimes(Integer skillId) {
        entityManager.createNativeQuery(
                "update skill set orderTimes = orderTimes+1 where skillId = :skillId")
                .setParameter("skillId", skillId).executeUpdate();
    }

    @Override
    public void increaseOrderedTimes(Integer skillId) {
        entityManager.createNativeQuery(
                "update skill set orderedTimes = orderedTimes+1 where skillId = :skillId")
                .setParameter("skillId", skillId).executeUpdate();
    }

}
