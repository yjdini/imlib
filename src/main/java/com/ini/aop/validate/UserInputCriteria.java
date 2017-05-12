package com.ini.aop.validate;

import java.lang.annotation.*;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface UserInputCriteria {
    String[] forbid() default {};
    String[] essential() default {};
}
