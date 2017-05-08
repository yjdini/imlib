package com.ini.service;

import com.ini.entity.User;
import com.utils.ConstJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Somnus`L on 2017/4/5.
 */

public class UserServiceImpl implements UserService{
    @Override
    public void test() {

    }

    @Override
    public ConstJson.Result addUser(User user) {
        return null;
    }

    @Override
    public ConstJson.Result editUser(User user, Integer userId) {
        return null;
    }

    @Override
    public ConstJson.Result validateUser(String phone, String password) {
        return null;
    }

    @Override
    public ConstJson.Result clearUserSession(HttpServletRequest request) {
        return null;
    }

    @Override
    public User getUserById(Integer integer) {
        return null;
    }

    @Override
    public ConstJson.Result uploadAvatar(String image) {
        return null;
    }

    @Override
    public Integer getSessionUid(HttpServletRequest request) {
        return null;
    }
}
