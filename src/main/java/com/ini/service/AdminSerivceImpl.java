package com.ini.service;

import com.ini.data.entity.*;
import com.ini.data.jpa.*;
import com.ini.data.schema.OrderUserSet;
import com.ini.data.schema.SkillTagSet;
import com.ini.service.abstrac.AdminService;
import com.ini.service.abstrac.SkillService;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired private SubRepository subRepository;
    @Autowired private SkillService skillService;

    @Autowired private SessionUtil sessionUtil;


    @Override
    public Admin login(String email, String password) {
        Admin admin = adminRepository.findByEmailAndPassword(email, password);
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
            user.setQualifyTime(new Date());

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
        List<OrderUserSet> fromOrders = orderRepository.getAllFromOrders(userId);
        List<OrderUserSet> toOrders = orderRepository.getAllToOrders(userId);
        List<SkillTagSet> skills = skillRepository.getSkillTags(userId);

        return ResultMap.ok().result("user", user)
                .result("applys", applys)
                .result("fromOrders",fromOrders)
                .result("toOrders",toOrders)
                .result("skills",skills)
                .getMap();
    }

    @Override
    @Transactional
    public boolean recoverUser(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user.getSubId().equals(sessionUtil.getSubId())) {
            user.setStatus(1);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Map getApplysByResult(Integer result) {
        Integer subId = sessionUtil.getSubId();
        List applys = applyRepository.getApplysByResultSubId(result, subId);
        return ResultMap.ok().result(applys).getMap();
    }

    @Override
    public Map getApplyById(Integer applyId) {
        Apply apply = applyRepository.findOne(applyId);
        User user = userRepository.findOne(apply.getUserId());
        if (!user.getSubId().equals(sessionUtil.getSubId())) {
            return ResultMap.error().setMessage("该申请用户不属于这个子系统，无法查看！").getMap();
        }
        return ResultMap.ok().result("apply", apply).result("user", user).getMap();
    }

    @Override
    @Transactional
    public Map editPassword(String oldPassword, String newPassword) {
        Integer adminId = sessionUtil.getAdminId();
        Admin admin = adminRepository.findOne(adminId);

        if (admin.getPassword().equals(oldPassword)) {
            admin.setPassword(newPassword);
            adminRepository.save(admin);
            return ResultMap.ok().getMap();
        }

        return ResultMap.error().setMessage("密码错误！").getMap();
    }

    @Override
    public Map getSubUrl() {
        Integer subId = sessionUtil.getSubId();
        Sub sub = subRepository.findOne(subId);
        return ResultMap.ok().result(sub.getToken()).getMap();
    }

    @Override
    @Transactional
    public boolean cancleRejectApply(Integer applyId) {
        Apply apply = applyRepository.findOne(applyId);
        User user = userRepository.findOne(apply.getUserId());
        if (user.getSubId().equals(sessionUtil.getSubId())) {
            apply.setResult(0);
            apply.setRejectReason("");
            applyRepository.save(apply);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean shelveMaster(Integer userId, String shelveReason) {
        User user = userRepository.findOne(userId);
        if (user.getSubId().equals(sessionUtil.getSubId())) {
            user.setShelveReason(shelveReason);
            user.setType("m-c");
            userRepository.save(user);
            skillRepository.shelveSkills(userId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean groundMaster(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user.getSubId().equals(sessionUtil.getSubId())) {
            user.setShelveReason("");
            user.setType("m");
            userRepository.save(user);
            skillRepository.groundSkills(userId);
            return true;
        }
        return false;
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminRepository.findOne(adminId);
    }

}
