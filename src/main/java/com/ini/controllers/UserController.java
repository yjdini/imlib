package com.ini.controllers;

import com.ini.aop.annotation.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.dao.entity.User;
import com.ini.service.UserService;
import com.utils.Request2Bean;
import com.utils.ResultMap;
import com.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
	private UserService userService;
    @Autowired
    private SessionUtil sessionUtil;


	@RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map addUser(@RequestBody User user)
    {
        return userService.addUser(user).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/edit")
    public Map editUser(HttpServletRequest request)
    {
        Integer userId = sessionUtil.getUserId();
        User user = userService.getUser();
        user = Request2Bean.Convert(request, user);
        return userService.updateUser(user).getMap();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map login(HttpServletRequest request, @RequestBody Map<String, String> body)
    {
        User user = userService.validateUser(body.get("nickname"), body.get("password"));
        if (user == null) {
            return ResultMap.error().setMessage("昵称或密码错误").getMap();
        } else {
            sessionUtil.setUser(user);
            return ResultMap.ok().put("result",user.getUserId()).getMap();
        }
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/logout")
    public Map logout(HttpServletRequest request, HttpServletResponse response)
    {
        sessionUtil.clearSession();
        return ResultMap.ok().getMap();
    }

    @RequestMapping(value = "/info/{userId}")
    public Map getUserById(@PathVariable String userId)
    {
        return userService.getUserById(new Integer(userId)).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/avatar/upload",method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map uploadAvatar(@RequestParam("image") MultipartFile image)
    {
        return userService.uploadAvatar(image).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/studentCard/upload",method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map uploadStudentCard(@RequestParam("image") MultipartFile image)
    {
            return userService.uploadStudentCard(image).getMap();
    }

    @RequestMapping(value = "/status")
    public Map getUserLoginStatus()
    {
        if (sessionUtil.logined()) {
            return ResultMap.ok().getMap();
        } else {
            return ResultMap.unlogin().getMap();
        }
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/isMaster")
    public Map isMaster()
    {
        if(userService.isMaster()) {
            return ResultMap.ok().put("result", 1).getMap();
        } else {
            return ResultMap.ok().put("result", 0).getMap();
        }
    }

}


