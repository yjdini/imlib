package com.ini.service.abstrac;

import com.ini.data.entity.Admin;
import com.ini.data.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public interface AdminService {

    Admin login(String email, String password);

    boolean deleteUser(Integer userId, String deleteReason);

    boolean proveApply(Integer userId);

    boolean rejectApply(Integer applyId, String rejectReason);

    List<User> getUsersByExample(User user);

    Map getUserAllInfo(Integer userId);

    boolean recoverUser(Integer userId);

    Map getApplysByResult(Integer result);

    Map getApplyById(Integer applyId);
}
