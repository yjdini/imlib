package com.ini.service;

import com.ini.dao.entity.Apply;
import com.ini.service.abstrac.ApplyService;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Somnus`L on 2017/5/4.
 *
 */
public class ApplyServiceImpl implements ApplyService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SessionUtil sessionUtil;

    @Override
    @Transactional
    public ResultMap addApply(Apply apply) {
        try {
            apply.setUserId(sessionUtil.getUserId());
            entityManager.persist(apply);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok().put("result", apply.getApplyId());
    }

    @Override
    public ResultMap getApplys() {
        List applys = entityManager.createQuery(
                "from Apply where userId = :userId and status = 1", Apply.class)
                .setParameter("userId", sessionUtil.getUserId()).getResultList();
        return ResultMap.ok().put("result", applys);

    }

    @Override
    public ResultMap getApplyDetail(Integer applyId) {
        return ResultMap.ok().put("result", entityManager.find(Apply.class, applyId));
    }

    @Override
    public ResultMap getLatestApply() {
        List result = entityManager.createQuery(
                "from Apply where userId = :userId and status = 1 orderBy applyId desc", Apply.class)
                .setParameter("userId", sessionUtil.getUserId())
                .getResultList();
        if (result.size() == 0) {
            return ResultMap.ok().put("result", null);
        } else {
            return ResultMap.ok().put("result", result.get(0));
        }
    }
}
