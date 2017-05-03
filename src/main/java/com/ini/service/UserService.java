package com.ini.service;

import com.ini.entity.User;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface UserService {
    void test();

    boolean addUser(User user);

    boolean editUser(User user);
}
