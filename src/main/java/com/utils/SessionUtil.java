package com.utils;

import com.ini.dao.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Somnus`L on 2017/5/9.
 */
public class SessionUtil {
    private static final ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<HttpServletRequest>();

    public void clearSession() {
        currentRequest.get().getSession().setAttribute("user", null);
    }

    public Integer getUserId() {
        User user = (User) currentRequest.get().getSession().getAttribute("user");
        if(user != null) {
            return user.getUserId();
        } else {
            return null;
        }
    }

    public Object get(String key) {
        return currentRequest.get().getSession().getAttribute(key);
    }

    public void set(String key, Object value) {
        currentRequest.get().getSession().setAttribute(key, value);
    }

    public User getUser() {
        return (User) currentRequest.get().getSession().getAttribute("user");
    }

    public void setUser(User user) {
        currentRequest.get().getSession().setAttribute("user", user);
    }

    public boolean logined() {
        return !(currentRequest.get().getSession().getAttribute("user") == null);
    }

    public static void bindRequest(HttpServletRequest request) {
        currentRequest.set(request);
    }
}
