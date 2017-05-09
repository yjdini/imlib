package com.ini.service;

import com.ini.dao.entity.User;
import com.utils.ConstJson;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface UserService {

    ConstJson.Result addUser(User user);

    ConstJson.Result updateUser(User user);

    User validateUser(String nickname, String password);

    User getUserById(Integer userId);

    ConstJson.Result uploadAvatar(String image);

    void increaseOrderTimes(Integer userId);

    void increaseOrderedTimes(Integer toUserId);
}
