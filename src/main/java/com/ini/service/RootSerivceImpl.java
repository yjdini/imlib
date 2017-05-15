package com.ini.service;

import com.ini.data.entity.Admin;
import com.ini.data.jpa.*;
import com.ini.service.abstrac.RootService;
import com.ini.utils.ConstJson;
import com.ini.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public class RootSerivceImpl implements RootService {
    @Autowired
    Environment environment;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ApplyRepository applyRepository;
    @Autowired
    OpenSubRepository openSubRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Map login(String name, String password) {
        if ("root@hust".equals(name) &&
                environment.getProperty("app.root.password").equals(password)) {
            return ResultMap.ok().result("name", name).getMap();
        } else {
            return ResultMap.error().setMessage("用户名或密码错误！").getMap();
        }
    }

    @Override
    public Map getSubList() {
        List<Admin> adminList = adminRepository.getAll();
        return ResultMap.ok().result(adminList).getMap();
    }



    @Override
    public Map closeSub(Integer subId, String closeReason) {
        return null;
    }

    @Override
    public Map getOpenSubList() {
        return null;
    }

    @Override
    public Map getSystemInfo() {
        Long subNum = adminRepository.count();
        Long openNum = openSubRepository.countByResult(0);
        Long userNum = userRepository.count();
        Long masterNum = userRepository.countByType("m");
        Long orderNum = ordersRepository.count();

        HashMap<String, Long> result = new HashMap<String, Long>();
        result.put("subNum", subNum);
        result.put("openNum", openNum);
        result.put("userNum", userNum);
        result.put("masterNum", masterNum);
        result.put("orderNum", orderNum);

        return ResultMap.ok().result(result).getMap();
    }
}
