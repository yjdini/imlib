package com.ini.service.implement;

import com.ini.dao.entity.Skill;
import com.ini.dao.schema.SkillTagSet;
import com.ini.dao.schema.SkillUserTagSet;
import com.ini.service.OrderService;
import com.ini.service.SkillService;
import com.utils.ConstJson;
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

    @Override
    @Transactional
    public ConstJson.Result addSkill(Skill skill) {
        try {
            entityManager.persist(skill);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK.setResult(skill.getSkillId().toString());
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
    public List<SkillTagSet> getSkillsByUserId(Integer userId) {
        return entityManager.createQuery("select new com.ini.dao.schema.SkillTagSet(s, t)" +
                " from Skill s, Tag t where s.userId = :userId and s.status = 1 and s.tagId = t.tagId", SkillTagSet.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public SkillTagSet getSkillDetail(Integer skillId) {
        return entityManager.createQuery("select new com.ini.dao.schema.SkillTagSet(s, t)" +
                " from Skill s, Tag t where s.skillId = :skillId and s.tagId = t.tagId", SkillTagSet.class)
                .setParameter("skillId", skillId)
                .getResultList()
                .get(0);
    }

    @Override
    public List searchByKeyword(String keyword, Integer subId) {
        return entityManager.createQuery("select new com.ini.dao.schema.SkillUserTagSet(s, u, t)" +
                        " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                        " and s.status = 1 and u.status = 1 and u.subId = :subId and " +
                        "(u.name like '%"+keyword+"%' or s.title like '%"+keyword+"%' )"
                ,SkillUserTagSet.class)
                .setParameter("subId", subId)
                .getResultList();
    }

    @Override
    public List searchByTagId(Integer tagId, Integer subId) {
        return entityManager.createQuery("select new com.ini.dao.schema.SkillUserTagSet(s, u, t)" +
                " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                " and s.status = 1 and u.status = 1 and u.subId = :subId and s.tagId = :tagId"
                        ,SkillUserTagSet.class)
                .setParameter("subId", subId)
                .setParameter("tagId", tagId).getResultList();
    }

    @Override
    public List searchAll(Integer subId) {
        return entityManager.createQuery("select new com.ini.dao.schema.SkillUserTagSet(s, u, t)" +
                " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
                " and s.status = 1 and u.status = 1 and u.subId = :subId", SkillUserTagSet.class)
                .setParameter("subId", subId)
                .getResultList();
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
