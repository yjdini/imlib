package com.ini.service;

import com.ini.data.entity.Admin;
import com.ini.data.entity.OpenSub;
import com.ini.data.jpa.*;
import com.ini.service.abstrac.RootService;
import com.ini.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        if ("root@hust".equals(name) && validatePassword(password)) {
            return ResultMap.ok().result("name", name).getMap();
        } else {
            return ResultMap.error().setMessage("用户名或密码错误！").getMap();
        }
    }

    private boolean validatePassword(String password) {
        return environment.getProperty("app.root.password").equals(password);
    }

    @Override
    public Map getSubList() {
        List<Admin> adminList = adminRepository.getAll();
        return ResultMap.ok().result(adminList).getMap();
    }



    @Override
    @Transactional
    public Map closeSub(Integer subId, String closeReason, String password) {
        if (validatePassword(password)) {
            Admin admin = adminRepository.findBySubId(subId);
            admin.setStatus(0);
            admin.setDeleteReason(closeReason);

            adminRepository.save(admin);
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("密码错误！").getMap();
        }
    }

    @Override
    public Map getOpenSubList(OpenSub openSub, Integer currentPage) {
        Pageable pageRequest = new PageRequest(currentPage,10);
        Page<OpenSub> openSubs = openSubRepository.findAll(Example.of(openSub), pageRequest);
        List<OpenSub> list = new ArrayList<OpenSub>();
        for (OpenSub os: openSubs) {
            list.add(os);
        }
        Long count = openSubRepository.count(Example.of(openSub));
        return ResultMap.ok().result("list", list).result("recordsNum", count).getMap();
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

    @Override
    public Map startSub(Integer subId) {
        Admin admin = adminRepository.findBySubId(subId);
        admin.setStatus(1);
        admin.setDeleteReason("");
        adminRepository.save(admin);
        return ResultMap.ok().getMap();
    }
}
