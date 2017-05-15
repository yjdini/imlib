package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.data.entity.User;
import com.ini.service.abstrac.UserService;
import com.ini.utils.Map2Bean;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;
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
    public Map addUser(@RequestBody Map<String, Object> body)
    {
        User user = Map2Bean.convert(body, new User(true), true);
        return userService.addUser(user).getMap();
    }


    @RequestMapping(value = "/comments/{userId}")
    public Map getComments(@PathVariable Integer userId)
    {
        return userService.getComments(userId);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map login(@RequestBody Map<String, Object> body)
    {
        User user = userService.validateUser((String)body.get("nickname"), (String)body.get("password"));
        if (user == null) {
            return ResultMap.error().setMessage("昵称或密码错误").getMap();
        } else if (user.getStatus().equals(0)) {
            String deleteReason = user.getDeleteReason();
            return ResultMap.error()
                    .setMessage("您的账号已被加入黑名单，原因为：" + deleteReason).getMap();
        } else {
            sessionUtil.setUser(user);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("userId", user.getUserId());
            result.put("subId", user.getSubId());
            result.put("type", user.getType());
            return ResultMap.ok().put("result", result).getMap();
        }
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/logout")
    public Map logout()
    {
        sessionUtil.clearSession();
        return ResultMap.ok().getMap();
    }


    @RequestMapping(value = "/info/{userId}")
    public Map getUserById(@PathVariable Integer userId)
    {
        return userService.getUserById(userId).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/info")
    public Map getUserById()
    {
        return userService.getUserById(sessionUtil.getUserId()).getMap();
    }

    @RequestMapping(value = "/sub/list")
    public Map getSubList()
    {
        return userService.getSubList().getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/avatar/upload",method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map uploadAvatar(@RequestParam("image") MultipartFile image)
    {
        return userService.uploadAvatar(image).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/edit" ,method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map editUser(@RequestBody Map<String, Object> body)
    {
        User user = userService.getUser();
        user = Map2Bean.convert(body, user, true);
        return userService.updateUser(user).getMap();
    }

    @RequestMapping(value = "/studentCard/upload",method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map uploadStudentCard(@RequestParam("image") MultipartFile image)
    {
            return userService.uploadStudentCard(image).getMap();
    }

    @RequestMapping(value = "/status")
    public Map getUserLoginStatus()
    {
        if (sessionUtil.logined()) {
            return getUserById();
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

    @RequestMapping(value = "/subid/{token}")
    public Map getSubId(@PathVariable String token)
    {
        return userService.getSubIdByToken(token);
    }

}


