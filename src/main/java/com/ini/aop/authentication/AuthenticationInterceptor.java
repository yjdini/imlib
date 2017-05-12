package com.ini.aop.authentication;

import com.ini.dao.entity.User;
import com.ini.utils.PrintUtil;
import com.ini.utils.ReflectUtil;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import com.ini.utils.convert.ResultMapConvert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Somnus`L on 2017/1/12.
 */
public final class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
    private final SessionUtil sessionUtil = new SessionUtil();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        SessionUtil.bindRequest(request);

        if (!(handler instanceof HandlerMethod))
        {
            return false;
        }
        Authentication authenti = ReflectUtil.getAnnotation((HandlerMethod) handler, Authentication.class);

        if (authenti == null)
        {
            return true;
        }

        AuthenticationType value = authenti.value();

        if (value == AuthenticationType.CommonUser)
        {
            if (sessionUtil.logined()) {
                return true;
            } else {
                PrintUtil.responseWithJson(response, new ResultMapConvert()
                        .convert(ResultMap.unlogin().setMessage("用户未登录")));
                return false;
            }
        }
        else if(value == AuthenticationType.NoValidate)
        {
            return true;
        }
        else if(value == AuthenticationType.Master)
        {
            User user = sessionUtil.getUser();
            if (user.getType().equals("c")) {
                PrintUtil.responseWithJson(response, new ResultMapConvert()
                        .convert(ResultMap.error().setMessage("需要行家用户权限")));
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    private void alertNoAuthority(String type, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.print("You don't have the " + type + " authority!");
        out.close();
    }
}
