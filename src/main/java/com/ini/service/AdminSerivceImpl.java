package com.ini.service;

import com.ini.dao.entity.Admin;
import com.ini.dao.schema.UserSet;
import com.ini.service.abstrac.AdminService;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public class AdminSerivceImpl implements AdminService {

    @Override
    public Admin login(String name, String password) {
        return null;
    }

    @Override
    public List<UserSet> getMasters() {
        return null;
    }

    @Override
    public List<UserSet> getCommonUsers() {
        return null;
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
}
