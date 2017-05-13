package com.ini.service;

import com.ini.data.entity.Skill;
import com.ini.data.jpa.SkillRepository;
import com.ini.data.schema.SkillTagSet;
import com.ini.data.schema.SkillUserTagSet;
import com.ini.service.abstrac.OrderService;
import com.ini.service.abstrac.SkillService;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * see SkillService
 * Created by Somnus`L on 2017/4/5.
 */
public class SkillServiceImpl implements SkillService {
    @PersistenceContext private EntityManager entityManager;

    @Autowired private SkillRepository skillRepository;

    @Autowired private OrderService orderService;
    @Autowired private SessionUtil sessionUtil;

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
            orderService.rejectAllOrdersOfSkill(skillId);
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
        List skills = skillRepository.getSkillTags(userId);
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap getSkillDetail(Integer skillId) {
        List skills = entityManager.createQuery("select new com.ini.data.schema.SkillTagSet(s, t)" +
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
        List skills = entityManager.createQuery("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
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
        List skills = entityManager.createQuery("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
                " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                " and s.status = 1 and u.status = 1 and u.subId = :subId and s.tagId = :tagId"
                        ,SkillUserTagSet.class)
                .setParameter("subId", subId)
                .setParameter("tagId", tagId).getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap searchAll(Integer subId) {
        List skills = entityManager.createQuery("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
                " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                " and s.status = 1 and u.status = 1 and u.subId = :subId", SkillUserTagSet.class)
                .setParameter("subId", subId)
                .getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap getSkillsByUserIdExcept(Integer userId, Integer exceptSkillId) {
        List skills = entityManager.createQuery("select new com.ini.data.schema.SkillTagSet(s, t)" +
                " from Skill s, Tag t where s.userId = :userId and s.status = 1 and s.tagId = t.tagId" +
                " and s.skillId != :skillId", SkillTagSet.class)
                .setParameter("userId", userId)
                .setParameter("skillId", exceptSkillId)
                .getResultList();
        return ResultMap.ok().put("result", skills);
    }

    @Override
    @Transactional
    public void addScore(Integer skillId, Integer score) {
        Skill skill = skillRepository.findOne(skillId);
        BigDecimal averageScore = skill.getScore();
        averageScore = (averageScore == null) ? new BigDecimal(0) : averageScore;
        Integer orderedTimes = skill.getOrderedTimes() - 1;
        averageScore = averageScore.multiply(new BigDecimal(orderedTimes))
                .add(new BigDecimal(score)).divide(new BigDecimal(orderedTimes + 1)).setScale(1);

        skill.setScore(averageScore);
        skillRepository.save(skill);
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
