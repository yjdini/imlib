package com.ini.aop.validate;

import com.utils.ResultMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
@Aspect
@Component
public class InputCheckAspect {
    @Around("execution(* com.ini.controllers.*.*(..)) " +
            "and @annotation(com.ini.aop.validate.UserInputCriteria)")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
//        Parameter[] parameters = method.getParameters();
//        for (Parameter parameter : parameters) {
//            if (parameter.isAnnotationPresent(RequestBody.class)) {
//            }
//        }

        UserInputCriteria criteria = method.getAnnotation(UserInputCriteria.class);
        String[] forbids = criteria.forbid();
        String[] essentials = criteria.essential();
        for (Object arg : args) {
            if (arg instanceof Map) {
                for (String forbid : forbids) {
                    if (((Map) arg).containsKey(forbid)) {
                        return ResultMap.error().setMessage("请求包含非法字段" + forbid);
                    }
                }

                for (String essential : essentials) {
                    if (((Map) arg).containsKey(essential)) {
                        return ResultMap.error().setMessage("请求缺少必须字段" + essential);
                    }
                }
            }
        }

        return point.proceed(args);
    }
}
