package com.ini.service;

import com.ini.entity.User;
import com.utils.ConstJson;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface UserService {
    void test();

    ConstJson.Result addUser(User user);

    ConstJson.Result editUser(User user, Integer userId);

    ConstJson.Result validateUser(String phone, String password);

    ConstJson.Result clearUserSession(HttpServletRequest request);

    User getUserById(Integer integer);

    ConstJson.Result uploadAvatar(String image);

    Integer getSessionUid(HttpServletRequest request);
}
