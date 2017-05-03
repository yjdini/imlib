package com.aop.annotation;

import com.aop.authentication.AuthenticationType;

import java.lang.annotation.*;

/**
 * Created by Somnus`L on 2017/1/12.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Authentication
{
    AuthenticationType value() default AuthenticationType.NoValidate;
}
