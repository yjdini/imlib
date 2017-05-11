package com.ini.service.implement;

import com.ini.dao.entity.Skill;
import com.ini.dao.schema.SkillTagSet;
import com.ini.dao.schema.SkillUserTagSet;
import com.ini.service.OrderService;
import com.ini.service.SkillService;
import com.mysql.cj.api.Session;
import com.utils.ConstJson;
import com.utils.ResultMap;
import com.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * see SkillService
 * Created by Somnus`L on 2017/4/5.
 */
public class SkillServiceImpl implements SkillService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderService orderService;
    @Autowired
    SessionUtil sessionUtil;

    @Override
    @Transactional
    public ResultMap addSkill(Skill skill) {
        try {
            skill.setUserId(sessionUtil.getUserId());
            entityManager.persist(skill);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return  ResultMap.ok().put("result", skill.getSkillId());
    }

    @Override
    @Transactional
    public ResultMap deleteSkill(Integer skillId) {
        try {
            Skill skill = entityManager.find(Skill.class, skillId);
            if (!skill.getUserId().equals(sessionUtil.getUserId())) {//没有权限
                return ResultMap.ok().setMessage("no authority");
            }
            orderService.rejectAllOrders(skillId);
            skill.setStatus(0);
            entityManager.persist(skill);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok();
    }

    @Override
    public ResultMap getSkillsByUserId(Integer userId) {
        List skills = entityManager.createQuery("select new com.ini.dao.schema.SkillTagSet(s, t)" +
                " from Skill s, Tag t where s.userId = :userId and s.status = 1 and s.tagId = t.tagId", SkillTagSet.class)
                .setParameter("userId", userId)
                .getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap getSkillDetail(Integer skillId) {
        List skills = entityManager.createQuery("select new com.ini.dao.schema.SkillTagSet(s, t)" +
                " from Skill s, Tag t where s.skillId = :skillId and s.tagId = t.tagId", SkillTagSet.class)
                .setParameter("skillId", skillId)
                .getResultList();
        if (skills.size() == 0) {
            return ResultMap.ok().put("result", null);
        } else {
            return ResultMap.ok().put("result", skills.get(0));
        }
    }

    @Override
    public ResultMap searchByKeyword(String keyword, Integer subId) {
        List skills = entityManager.createQuery("select new com.ini.dao.schema.SkillUserTagSet(s, u, t)" +
                        " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                        " and s.status = 1 and u.status = 1 and u.subId = :subId and " +
                        "(u.name like '%"+keyword+"%' or s.title like '%"+keyword+"%' )"
                ,SkillUserTagSet.class)
                .setParameter("subId", subId)
                .getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap searchByTagId(Integer tagId, Integer subId) {
        List skills = entityManager.createQuery("select new com.ini.dao.schema.SkillUserTagSet(s, u, t)" +
                " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                " and s.status = 1 and u.status = 1 and u.subId = :subId and s.tagId = :tagId"
                        ,SkillUserTagSet.class)
                .setParameter("subId", subId)
                .setParameter("tagId", tagId).getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap searchAll(Integer subId) {
        List skills = entityManager.createQuery("select new com.ini.dao.schema.SkillUserTagSet(s, u, t)" +
                " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                " and s.status = 1 and u.status = 1 and u.subId = :subId", SkillUserTagSet.class)
                .setParameter("subId", subId)
                .getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap getSkillsByUserIdExcept(Integer userId, Integer exceptSkillId) {
        List skills = entityManager.createQuery("select new com.ini.dao.schema.SkillTagSet(s, t)" +
                " from Skill s, Tag t where s.userId = :userId and s.status = 1 and s.tagId = t.tagId" +
                " and s.skillId != :skillId", SkillTagSet.class)
                .setParameter("userId", userId)
                .setParameter("skillId", exceptSkillId)
                .getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public void increaseOrderTimes(Integer skillId) {
        entityManager.createNativeQuery(
                "update Skill set orderTimes = orderTimes+1 where skillId = :skillId")
                .setParameter("skillId", skillId).executeUpdate();
    }

    @Override
    public void increaseOrderedTimes(Integer skillId) {
        entityManager.createNativeQuery(
                "update Skill set orderedTimes = orderedTimes+1 where skillId = :skillId")
                .setParameter("skillId", skillId).executeUpdate();
    }

}
