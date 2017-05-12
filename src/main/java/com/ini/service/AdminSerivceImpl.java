package com.ini.service;

import com.ini.data.entity.Admin;
import com.ini.data.entity.Apply;
import com.ini.data.entity.Orders;
import com.ini.data.entity.User;
import com.ini.data.jpa.*;
import com.ini.data.schema.OrderUserSet;
import com.ini.data.schema.SkillTagSet;
import com.ini.service.abstrac.AdminService;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */
public class AdminSerivceImpl implements AdminService {
    @Autowired private AdminRepository adminRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ApplyRepository applyRepository;
    @Autowired private OrdersRepository orderRepository;
    @Autowired private SkillRepository skillRepository;

    @Autowired
    private SessionUtil sessionUtil;



    @Override
    public Admin login(String name, String password) {
        Admin admin = adminRepository.findByNameAndPassword(name, password);
        if (admin != null && admin.getStatus().equals(1)) {
            sessionUtil.setAdmin(admin);
        }
        return admin;
    }

    @Override
    public List<User> getUsersByExample(User user) {
        return (List<User>) userRepository.findAll(Example.of(user));
    }

    @Transactional
    @Override
    public boolean deleteUser(Integer userId, String deleteReason) {
        User user = userRepository.findOne(userId);
        if (user.getSubId().equals(sessionUtil.getSubId())) {
            user.setStatus(0);
            user.setDeleteReason(deleteReason);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean proveApply(Integer applyId) {
        Apply apply = applyRepository.findOne(applyId);
        User user = userRepository.findOne(apply.getUserId());
        if (user.getSubId().equals(sessionUtil.getSubId())) {
            apply.setResult(1);
            user.setType("m");
            user.setWechat(apply.getWechat());
            user.setTitle(apply.getTitle());
            user.setName(apply.getName());
            user.setWorks(apply.getWorks());
            user.setIntroduce(apply.getIntroduce());

            applyRepository.save(apply);
            userRepository.save(user);

            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean rejectApply(Integer applyId, String rejectReason) {
        Apply apply = applyRepository.findOne(applyId);
        User user = userRepository.findOne(apply.getUserId());
        if (user.getSubId().equals(sessionUtil.getSubId())) {
            apply.setRejectReason(rejectReason);
            apply.setResult(2);

            applyRepository.save(apply);

            return true;
        }
        return false;
    }

    @Override
    public Map getUserAllInfo(Integer userId) {
        User user = userRepository.findOne(userId);
        if (!user.getSubId().equals(sessionUtil.getSubId())) {
            return ResultMap.error().setMessage("该用户不属于这个子系统！").getMap();
        }

        List<Apply> applys = applyRepository.findByUserId(userId);
        List<OrderUserSet> fromOrders = orderRepository.getFromOrders(userId);
        List<OrderUserSet> toOrders = orderRepository.getToOrders(userId);
        List<SkillTagSet> skills = skillRepository.getSkillTags();

        return ResultMap.ok().result("user", user)
                .result("applys", applys)
                .result("fromOrders",fromOrders)
                .result("toOrders",toOrders)
                .result("skills",skills)
                .getMap();
    }

}
