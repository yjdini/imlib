package com.aop.authentication;

import com.aop.annotation.Authentication;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by Somnus`L on 2017/1/12.
 */
//@Aspect
//@Component
public final class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if(handler instanceof HandlerMethod)
        {
            HandlerMethod hm = (HandlerMethod) handler;
            Class clazz = hm.getBeanType();
            Method m = hm.getMethod();
            if(clazz != null && m != null)
            {
                boolean isClazzAnnotate = clazz.isAnnotationPresent(Authentication.class);
                boolean isMethodAnnotate = m.isAnnotationPresent(Authentication.class);
                Authentication authenti = null;
                //根据annotation 类型，初始化authentication 的方式
                if(isClazzAnnotate)
                {
                    authenti = (Authentication) clazz.getAnnotation(Authentication.class);
                }
                else if(isMethodAnnotate)
                {
                    authenti = (Authentication) m.getAnnotation(Authentication.class);
                }

                if(authenti != null)
                {
                    //普通用户验证
                    if(authenti.value() == AuthenticationType.User)
                    {
                        HttpSession session = request.getSession();
                        if(session == null) return false;
                        String userType = (String) session.getAttribute("user");
                        if("common".equals(userType))
                        {
                            return true;
                        }
                        else
                        {
                            alertNoAuthority("common user", response);
                            return false;
                        }
                    }//不用验证
                    else if(authenti.value() == AuthenticationType.NoValidate)
                    {
                        return true;
                    }//系统用户验证
                    else if(authenti.value() == AuthenticationType.SystemUser)
                    {
                        HttpSession session = request.getSession();
                        if(session == null) return false;
                        String userType = (String) session.getAttribute("user");
                        if("system".equals(userType))
                        {
                            return true;
                        }
                        else
                        {
                            alertNoAuthority("system user", response);
                            return false;
                        }
                    }
                }
                else//no annotation indicate the authentication , so just do nothing
                {
                    return true;
                }
            }
        }
        alertNoAuthority("", response);
        return false;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.print("");
    }

    private void alertNoAuthority(String type, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("You don't have the " + type + " authority!");
        out.close();
    }
}
