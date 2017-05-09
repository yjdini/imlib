package com.ini.service.implement;

import com.ini.dao.entity.Skill;
import com.ini.dao.schema.SkillTagSet;
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
        return entityManager.createNativeQuery("select  Skill.*, User.*, Tag.name as tagname  from Skill, " +
                        " User, Tag  where tag.tagId = skill.tagId and skill.userId" +
                        " = user.userId and skill.status = 1 and user.status = 1 and user.subId = ? and " +
                        "(user.nickname like BINARY '%"+keyword+"%' or skill.title like BINARY '%"+keyword+"%' )",
                "SkillUserEntity")
                .setParameter(1, subId)
                .getResultList();
    }

    @Override
    public List searchByTagId(Integer tagId, Integer subId) {
        return entityManager.createNativeQuery(
                "select  Skill.*, User.*, Tag.name as tagname  from Skill, User, Tag  where" +
                        " tag.tagId = skill.tagId and Skill.userId = User.userId" +
                        " and skill.status = 1 and user.status = 1 and user.subId = :subId " +
                        " and skill.tagId = :tagId",
                "SkillUserEntity")
                .setParameter("subId", subId)
                .setParameter("tagId", tagId).getResultList();
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
