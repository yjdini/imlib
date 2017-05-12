package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.data.entity.Admin;
import com.ini.data.entity.User;
import com.ini.data.utils.EntityUtil;
import com.ini.service.abstrac.AdminService;
import com.ini.service.abstrac.UserService;
import com.ini.utils.Map2Bean;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
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
    private SessionUtil sessionUtil;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login")
    public Map login(@RequestBody Map<String, Object> body)
    {
        String name = (String)body.get("name");
        String password = (String)body.get("password");
        Admin admin = adminService.login(name, password);
        if (admin == null) {
            return ResultMap.error().setMessage("用户名或密码错误！").getMap();
        } else if (admin.getStatus() == 0) { //用户被冻结
            return ResultMap.error()
                    .setMessage("您的账号已经被冻结，原因为：" + admin.getDeleteReason()).getMap();
        } else {
            return ResultMap.ok().result(
                    EntityUtil.all(admin).remove("password").getMap()).getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/list/logout")
    public Map logout()
    {
        sessionUtil.clearSession();
        return ResultMap.ok().getMap();
    }


    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/user/list")
    public Map getUsers(@RequestBody Map<String, Object> body)
    {
        User user = Map2Bean.convert(body, new User(), true);
        user.setSubId(sessionUtil.getSubId());

        List<User> users = adminService.getUsersByExample(user);
        return ResultMap.ok().result(users).getMap();
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/deleteuser/{userId}")
    public Map deleteUser(@PathVariable Integer userId, @RequestBody Map<String, Object> body)
    {
        String deleteReason = (String) body.get("deleteReason");
        if (adminService.deleteUser(userId, deleteReason)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("不能删除该用户！").getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/proveapply/{applyId}")
    public Map proveApply(@PathVariable Integer applyId)
    {
        if (adminService.proveApply(applyId)) {
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

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/userinfo/{userId}")
    public Map getUserAllInfo(@PathVariable Integer userId)
    {
        return adminService.getUserAllInfo(userId);
    }
}
