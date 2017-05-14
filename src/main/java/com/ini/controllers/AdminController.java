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
        String email = (String)body.get("email");
        String password = (String)body.get("password");
        Admin admin = adminService.login(email, password);
        if (admin == null) {
            return ResultMap.error().setMessage("邮箱或密码错误！").getMap();
        } else if (admin.getStatus() == 0) { //用户被冻结
            return ResultMap.error()
                    .setMessage("您的账号已经被冻结，原因为：" + admin.getDeleteReason()).getMap();
        } else {
            return ResultMap.ok().result(
                    EntityUtil.all(admin).remove("password").getMap()).getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/logout")
    public Map logout()
    {
        sessionUtil.clearSession();
        return ResultMap.ok().getMap();
    }


    @RequestMapping(value = "/status")
    public Map status(@RequestBody Map<String, Object> body)
    {
        if (sessionUtil.adminLogined()) {
            return ResultMap.ok().getMap();
        } else {
            return  ResultMap.unlogin().getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/info")
    public Map info()
    {
        Admin admin = adminService.getAdminById(sessionUtil.getAdminId());
        return ResultMap.ok().result(admin).getMap();
    }



    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/editpassword")
    public Map editPassword(@RequestBody Map<String, String> body)
    {
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        return adminService.editPassword(oldPassword, newPassword);
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/token")
    public Map getSubUrl()
    {
        return adminService.getSubUrl();
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
            return ResultMap.error().setMessage("该用户不属于这个分站，不能冻结该用户！").getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/recoveruser/{userId}")
    public Map recoverUser(@PathVariable Integer userId)
    {
        if (adminService.recoverUser(userId)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("该用户不属于这个分站，不能恢复该用户！").getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/proveapply/{applyId}")
    public Map proveApply(@PathVariable Integer applyId)
    {
        if (adminService.proveApply(applyId)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("该用户不属于这个分站，不能批准该用户的申请！").getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/rejectapply")
    public Map rejectApply(@RequestBody Map<String, Object> body)
    {
        Integer applyId = (Integer) body.get("applyId");
        String rejectReason = (String)body.get("rejectReason");
        if (adminService.rejectApply(applyId, rejectReason)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("该用户不属于这个分站，不能拒绝该用户的申请！").getMap();
        }
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/canclereject")
    public Map cancleRejectApply(@PathVariable Integer applyId)
    {
        if (adminService.cancleRejectApply(applyId)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("该用户不属于这个分站，不能撤销！").getMap();
        }
    }


    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/userinfo/{userId}")
    public Map getUserAllInfo(@PathVariable Integer userId)
    {
        return adminService.getUserAllInfo(userId);
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/applylist")
    public Map getApplyList(@RequestBody Map<String, Object> body) {
        Integer result = (Integer) body.get("result");
        return adminService.getApplysByResult(result);
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/applyinfo/{applyId}")
    public Map getApplyById(@PathVariable Integer applyId) {
        return adminService.getApplyById(applyId);
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/mastershelve/{userId}")
    public Map shelveMaster(@PathVariable Integer userId, @RequestBody Map<String, Object> body) {
        String shelveReason = (String) body.get("shelveReason");
        if (adminService.shelveMaster(userId, shelveReason)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("该用户不属于这个分站，不能下架！").getMap();
        }
    }


    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping(value = "/masterground/{userId}")
    public Map groundMaster(@PathVariable Integer userId) {
        if (adminService.groundMaster(userId)) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.error().setMessage("该用户不属于这个分站，不能上架！").getMap();
        }
    }


}
