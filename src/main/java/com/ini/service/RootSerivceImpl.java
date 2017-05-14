package com.ini.service;

import com.ini.service.abstrac.RootService;
import com.ini.utils.ConstJson;
import com.ini.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public class RootSerivceImpl implements RootService {
    @Autowired
    Environment environment;

    @Override
    public Map login(String name, String password) {
        if ("root@hust".equals(name) &&
                environment.getProperty("app.root.password").equals(password)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("用户名或密码错误！").getMap();
        }
    }

    @Override
    public Map getSubList() {
        return null;
    }

    @Override
    public Map closeSub(Integer subId, String closeReason) {
        return null;
    }

    @Override
    public Map getOpenSubList() {
        return null;
    }
}
