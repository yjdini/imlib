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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            skill.setSubId(sessionUtil.getSubId());
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
        List skills = skillRepository.getSkillsByUserId(userId);
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
    public ResultMap getSkillsByUserIdExcept(Integer userId, Integer exceptSkillId) {
        List skills = skillRepository.getSkillsByUserIdExcept(userId, exceptSkillId);
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public void addScore(Integer skillId, Integer score) {
        Skill skill = skillRepository.findOne(skillId);
        BigDecimal oldScore = skill.getScore();
        oldScore = (oldScore == null) ? new BigDecimal(0) : oldScore;
        Integer orderedTimes = skill.getOrderedTimes();
        orderedTimes = (orderedTimes == null) ? 0 : orderedTimes;
        BigDecimal newScore = oldScore.multiply(new BigDecimal(orderedTimes))
                .add(new BigDecimal(score))
                .divide(new BigDecimal(orderedTimes + 1))
                .setScale(1,BigDecimal.ROUND_DOWN);

        skill.setSeoScore(computeSeoScore(skill));
        skillRepository.save(skill);
    }

    private Integer computeSeoScore(Skill skill) {
        BigDecimal score = skill.getScore() == null ? new BigDecimal(3) : skill.getScore();
        Integer showTimes = skill.getShowTimes();
        showTimes = (showTimes == null) ? 0 : showTimes;
        Integer orderTimes = skill.getOrderTimes();
        orderTimes = (orderTimes == null) ? 0 : orderTimes;
        Integer orderedTimes = skill.getOrderedTimes();
        orderedTimes = (orderedTimes == null) ? 0 : orderedTimes;

        return score.multiply(
                new BigDecimal(showTimes + orderTimes*3 + orderedTimes * 3))
                .intValue();

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


    @Override
    public void increaseShowTime(Integer skillId) {
        Skill skill = skillRepository.findOne(skillId);
        if (skill == null) {
            return;
        }
        skill.setSeoScore(computeSeoScore(skill));
        skill.setShowTimes(skill.getShowTimes() + 1);
        skillRepository.save(skill);
    }

    /**
     * index interface impl
     */
    @Override
    public ResultMap searchPageHotest(Integer subId, Integer tagId, Integer currentPage) {
        Pageable pageRequest = new PageRequest(currentPage,10);
        List<SkillUserTagSet> list = skillRepository.getHotest(subId, tagId, pageRequest);
        return ResultMap.ok().result(list);
    }

    @Override
    public ResultMap searchPageHotest(Integer subId, Integer currentPage) {
        Pageable pageRequest = new PageRequest(currentPage,10);
        List<SkillUserTagSet> list = skillRepository.getHotest(subId, pageRequest);
        return ResultMap.ok().result(list);
    }

    @Override
    public ResultMap searchPageByKeyword(String keyword, Integer subId, Integer currentPage) {
        Pageable pageRequest = new PageRequest(currentPage,10);
        List<SkillUserTagSet> skills = skillRepository.pageQueryByKeyword(keyword, subId, pageRequest);
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap searchPageByTagId(Integer tagId, Integer subId, Integer currentPage) {
        Pageable pageRequest = new PageRequest(currentPage,10);
        List<SkillUserTagSet> skills = skillRepository.pageQueryByTagId(tagId, subId, pageRequest);
        return ResultMap.ok().put("result", skills);
    }

    @Override
    public ResultMap searchPageAll(Integer subId, Integer currentPage) {
        Pageable pageRequest = new PageRequest(currentPage,10);
        List<SkillUserTagSet> skills = skillRepository.pageQueryBySubId(subId, pageRequest);
        return ResultMap.ok().put("result", skills);
    }


}
