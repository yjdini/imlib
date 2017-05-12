package com.ini.aop.validate;

import com.utils.PrintUtil;
import com.utils.ReflectUtil;
import com.utils.ResultMap;
import com.utils.convert.ResultMapConvert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/12.
 * 用户输入检验，
 * 只适用于request.getParameter()中的参数，因此使用的范围很局限
 */
public class UserInputVerifyInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (true) {
            return true;
        }
        if (!(handler instanceof HandlerMethod))
        {
            return false;
        }
        UserInputCriteria criteria = ReflectUtil.getAnnotation((HandlerMethod) handler, UserInputCriteria.class);

        if (hasForbidParameter(request, criteria.forbid())) {
            PrintUtil.responseWithJson(response, new ResultMapConvert()
                    .convert(ResultMap.error().setMessage("用户输入含有非法字段")));
            return false;
        }

        if (lackEssentialParameter(request, criteria.essential())) {
            PrintUtil.responseWithJson(response, new ResultMapConvert()
                    .convert(ResultMap.error().setMessage("用户输入缺少必需字段")));
            return false;
        }
        return true;
    }

    private boolean lackEssentialParameter(HttpServletRequest request, String[] essentials) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String essential : essentials) {
            if (!parameterMap.containsKey(essential)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasForbidParameter(HttpServletRequest request, String[] forbids) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String forbid : forbids) {
            if (parameterMap.containsKey(forbid)) {
                return true;
            }
        }
        return false;
    }
}
