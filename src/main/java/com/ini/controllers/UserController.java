package com.ini.controllers;

import java.util.List;

import com.aop.annotation.Authentication;
import com.aop.authentication.AuthenticationType;
import com.ini.entity.UserRepository;
import com.ini.service.UserService;
import com.utils.ConstJson;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;


import com.ini.entity.User;
import com.mongodb.WriteResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
	private UserService userService;

	@RequestMapping(value = "/addUser",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/editUser",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result editUser(@RequestBody User user, HttpServletRequest request)
    {
        return userService.editUser(user, userService.getSessionUid(request));
    }

    @RequestMapping(value = "/login")
    public ConstJson.Result login(HttpServletRequest request, HttpServletResponse response)
    {
        return userService.validateUser(request.getParameter("phone"), request.getParameter("password"));
    }

    @RequestMapping(value = "/logout")
    public ConstJson.Result logout(HttpServletRequest request, HttpServletResponse response)
    {
        return userService.clearUserSession(request);
    }

    @RequestMapping(value = "/getUserById/{userId}")
    public User getUserById(@PathVariable String userId)
    {
        return userService.getUserById(new Integer(userId));
    }

    @RequestMapping(value = "/uploadAvatar",method = RequestMethod.POST , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ConstJson.Result uploadAvatar(HttpServletRequest request, HttpServletResponse response)
    {
        return userService.uploadAvatar(request.getParameter("image"));
    }




}


