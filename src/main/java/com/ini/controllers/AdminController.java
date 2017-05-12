package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.dao.entity.Admin;
import com.ini.dao.entity.User;
import com.ini.dao.schema.UserSet;
import com.ini.dao.utils.EntityUtil;
import com.ini.service.abstrac.AdminService;
import com.ini.service.abstrac.UserService;
import com.utils.ConstJson;
import com.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/list/master")
    public Map getMasters()
    {
        List<UserSet> masters = adminService.getMasters();
        return ResultMap.ok().result(masters).getMap();
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/list/common")
    public Map getCommonUsers()
    {
        List<UserSet> commonUsers = adminService.getCommonUsers();
        return ResultMap.ok().result(commonUsers).getMap();
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/deleteuser/{userId}")
    public Map deleteUser(@PathVariable Integer userId)
    {
        if (adminService.deleteUser(userId)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("不能删除该用户！").getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/proveapply/{userId}")
    public Map proveApply(@PathVariable Integer userId)
    {
        if (adminService.proveApply(userId)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("不能批准该用户的申请！").getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/rejectapply")
    public Map rejectApply(@RequestBody Map<String, Object> body)
    {
        Integer userId = (Integer) body.get("userId");
        String rejectReason = (String)body.get("rejectReason");
        if (adminService.rejectApply(userId, rejectReason)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("不能拒绝该用户的申请！").getMap();
        }
    }

    @RequestMapping(value = "/list/login")
    public Map login(@RequestBody Map<String, Object> body)
    {
        String name = (String)body.get("name");
        String password = (String)body.get("password");
        Admin admin = adminService.login(name, password);
        if (admin == null) {
            return ResultMap.error().setMessage("用户名或密码错误！").getMap();
        } else {
            return ResultMap.ok().result(
                    EntityUtil.all(admin).remove("password").getMap()).getMap();
        }
    }

}
