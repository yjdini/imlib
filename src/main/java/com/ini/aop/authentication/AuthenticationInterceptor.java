package com.ini.aop.authentication;

import com.ini.aop.annotation.Authentication;
import com.ini.dao.entity.User;
import com.utils.PrintUtil;
import com.utils.ResultMap;
import com.utils.SessionUtil;
import com.utils.convert.ResultMapConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by Somnus`L on 2017/1/12.
 */
//@Aspect
@Component
public final class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
    private final SessionUtil sessionUtil = new SessionUtil();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        SessionUtil.bindRequest(request);

        if(handler instanceof HandlerMethod)
        {
            HandlerMethod hm = (HandlerMethod) handler;
            Class clazz = hm.getBeanType();
            Method method = hm.getMethod();
            if (clazz != null && method != null)
            {
                boolean isClazzAnnotate = clazz.isAnnotationPresent(Authentication.class);
                boolean isMethodAnnotate = method.isAnnotationPresent(Authentication.class);
                Authentication authenti = null;
                //根据annotation 类型，初始化authentication 的方式
                if (isClazzAnnotate) {
                    authenti = (Authentication) clazz.getAnnotation(Authentication.class);
                }
                else if (isMethodAnnotate) {
                    authenti = (Authentication) method.getAnnotation(Authentication.class);
                }

                if (authenti != null) {
                    //用户登录验证
                    if (authenti.value() == AuthenticationType.CommonUser) {
                        if (sessionUtil.logined()) {
                            return true;
                        } else {
                            PrintUtil.responseWithJson(response, new ResultMapConvert()
                                    .convert(ResultMap.unlogin().setMessage("用户未登录")));
                            return false;
                        }
                    } else if( authenti.value() == AuthenticationType.NoValidate) {//不用验证
                        return true;
                    } else if(authenti.value() == AuthenticationType.Master) {//行家用户验证
                        User user = sessionUtil.getUser();
                        if (user.getType() == 'c') {
                            PrintUtil.responseWithJson(response, new ResultMapConvert()
                                    .convert(ResultMap.error().setMessage("需要行家用户权限")));
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
                else//no annotation indicate the authentication , so just do nothing
                {
                    return true;
                }
            }
        }
        return false;
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
