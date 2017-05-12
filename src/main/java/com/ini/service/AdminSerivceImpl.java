package com.ini.service;

import com.ini.dao.entity.Admin;
import com.ini.dao.entity.User;
import com.ini.dao.schema.UserSet;
import com.ini.service.abstrac.AdminService;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public class AdminSerivceImpl implements AdminService {

    @Autowired
    private QueryByExampleExecutor exampleExecutor;

    @Autowired
    private SessionUtil sessionUtil;

    @Override
    public Admin login(String name, String password) {
//        Admin admin = userRepository.validateAdmin(name, password);
        Admin  admin = null;
        if (admin != null) {
            sessionUtil.setAdmin(admin);
        }
        return admin;
    }

    @Override
    public List<UserSet> getMasters() {
        Integer subId = sessionUtil.getAdmin().getSubId();
        return null;
//        return userRepository.getSubMasters(subId);
    }

    @Override
    public List<UserSet> getCommonUsers() {
        Integer subId = sessionUtil.getAdmin().getSubId();
        return null;
//        return userRepository.getSubCommonUsers(subId);
    }

    @Override
    public boolean deleteUser(Integer userId) {
        return false;
    }

    @Override
    public boolean proveApply(Integer userId) {
        return false;
    }

    @Override
    public boolean rejectApply(Integer userId, String rejectReason) {
        return false;
    }

    @Override
    public List<User> getUsersByExample(User user) {
        return (List<User>) exampleExecutor.findAll(Example.of(user));
    }
}
