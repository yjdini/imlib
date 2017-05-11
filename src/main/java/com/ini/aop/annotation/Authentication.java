package com.ini.aop.annotation;

import com.ini.aop.authentication.AuthenticationType;

import java.lang.annotation.*;

/**
 * Created by Somnus`L on 2017/1/12.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Authentication
{
    AuthenticationType value() default AuthenticationType.NoValidate;
}
