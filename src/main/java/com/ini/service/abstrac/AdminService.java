package com.ini.service.abstrac;

import com.ini.dao.entity.Admin;
import com.ini.dao.schema.UserSet;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public interface AdminService {

    Admin login(String name, String password);

    List<UserSet> getMasters();

    List<UserSet> getCommonUsers();

    boolean deleteUser(Integer userId);

    boolean proveApply(Integer userId);

    boolean rejectApply(Integer userId, String rejectReason);
}
