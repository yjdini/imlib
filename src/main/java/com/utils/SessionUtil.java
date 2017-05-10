package com.utils;

import com.ini.dao.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Somnus`L on 2017/5/9.
 */
public class SessionUtil {

    public ConstJson.Result clearSession(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return ConstJson.OK;
    }

    public Integer getUserId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            return user.getUserId();
        } else {
            return null;
        }
    }


    public void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    public boolean logined(HttpServletRequest request) {
        return !(request.getSession().getAttribute("user") == null);
    }
}
