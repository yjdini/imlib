package com.ini.utils;

import com.ini.data.entity.Admin;
import com.ini.data.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Somnus`L on 2017/5/9.
 *
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

    public String getUserType() {
        User user = (User) currentRequest.get().getSession().getAttribute("user");
        return user == null ? null : user.getType();
    }

//    public User getUser() {
//        return (User) currentRequest.get().getSession().getAttribute("user");
//    }

    public void setUser(User user) {
        currentRequest.get().getSession().setAttribute("user", user);
    }

    public boolean logined() {
        return currentRequest.get().getSession().getAttribute("user") != null;
    }

    public static void bindRequest(HttpServletRequest request) {
        currentRequest.set(request);
    }

    public void setAdmin(Admin admin) {
        currentRequest.get().getSession().setAttribute("admin", admin);
    }
    public Admin getAdmin() {
        return (Admin) currentRequest.get().getSession().getAttribute("admin");
    }
    public Integer getSubId() {
        if(true) return 1;
        Admin admin = (Admin) currentRequest.get().getSession().getAttribute("admin");
        return admin == null ? null : admin.getSubId();
    }
    public Integer getAdminId() {
        if(true) return 1;
        Admin admin = (Admin) currentRequest.get().getSession().getAttribute("admin");
        if(admin != null) {
            return admin.getAdminId();
        } else {
            return null;
        }
    }
    public boolean adminLogined() {
        return currentRequest.get().getSession().getAttribute("admin") != null;
    }

}
