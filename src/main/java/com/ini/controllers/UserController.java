package com.ini.controllers;

import com.ini.entity.User;
import com.ini.service.UserService;
import com.sun.tools.internal.jxc.ap.Const;
import com.utils.ConstJson;
import com.utils.Request2Bean;
import com.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
	private UserService userService;
    @Autowired
    private SessionUtil sessionUtil;


	@RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/edit")
    public ConstJson.Result editUser(HttpServletRequest request)
    {
        Integer userId = sessionUtil.getUserId(request);
        User user = userService.getUserById(userId);
        user = Request2Bean.Convert(request, user);
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/login")
    public ConstJson.Result login(HttpServletRequest request, HttpServletResponse response)
    {
        User user = userService.validateUser(request.getParameter("nickname"), request.getParameter("password"));
        if (user == null) {
            return ConstJson.ERROR;
        } else {
            sessionUtil.setUser(request, user);
            return ConstJson.OK;
        }
    }

    @RequestMapping(value = "/logout")
    public ConstJson.Result logout(HttpServletRequest request, HttpServletResponse response)
    {
        return sessionUtil.clearSession(request);
    }

    @RequestMapping(value = "/info/{userId}")
    public User getUserById(@PathVariable String userId)
    {
        return userService.getUserById(new Integer(userId));
    }

    @RequestMapping(value = "/avatar/upload",method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ConstJson.Result uploadAvatar(HttpServletRequest request, HttpServletResponse response)
    {
        return userService.uploadAvatar(request.getParameter("image"));
    }
}


