package com.ini.service;

import com.ini.data.entity.Apply;
import com.ini.data.entity.User;
import com.ini.data.jpa.ApplyRepository;
import com.ini.data.jpa.UserRepository;
import com.ini.service.abstrac.ApplyService;
import com.ini.service.abstrac.UserService;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Somnus`L on 2017/5/4.
 *
 */
public class ApplyServiceImpl implements ApplyService {
    @PersistenceContext private EntityManager entityManager;
    @Autowired private SessionUtil sessionUtil;
    @Autowired private ApplyRepository applyRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;

    @Override
    @Transactional
    public ResultMap addApply(Apply apply) {
        applyRepository.save(apply);

        User user = userRepository.findOne(sessionUtil.getUserId());
        user.setApplyId(apply.getApplyId());
        userRepository.save(user);

        return ResultMap.ok().result("applyId", apply.getApplyId())
                .result("avatar", user.getAvatar());
    }

    @Override
    public ResultMap getApplys() {
        Integer userId = sessionUtil.getUserId();
        List applys = applyRepository.findByUserId(userId);
        return ResultMap.ok().put("result", applys);
    }

    @Override
    public ResultMap getApplyDetail(Integer applyId) {
        return ResultMap.ok().put("result", entityManager.find(Apply.class, applyId));
    }

    @Override
    public ResultMap getLatestApply() {
        User user = userService.getUser();
        Integer applyId = user.getApplyId();
        if (applyId == null) {
            return ResultMap.ok().put("result", new HashMap());
        } else {
            Apply apply = applyRepository.findOne(user.getApplyId());
            return ResultMap.ok().put("result", apply);
        }

    }
}
