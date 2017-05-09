package com.ini.service.implement;

import com.ini.entity.Apply;
import com.ini.service.ApplyService;
import com.utils.ConstJson;
import org.joda.time.DateTime;
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

    @Override
    @Transactional
    public ConstJson.Result addApply(Apply apply) {
        try {
            entityManager.persist(apply);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK;
    }

    @Override
    public List<Apply> getApplys(Integer userId) {
        return entityManager.createQuery(
                "from apply where userId = :userId and status = 1", Apply.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public Apply getApplyDetail(Integer applyId) {
        return entityManager.find(Apply.class, applyId);
    }

    @Override
    public Apply getLatestApply(Integer userId) {
        return entityManager.createQuery(
                "from apply where userId = :userId and status = 1 orderBy applyId desc", Apply.class)
                .setParameter("userId", userId)
                .getResultList().get(0);
    }
}
