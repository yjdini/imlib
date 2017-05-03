package com.aop.authentication;

/**
 * Created by Somnus`L on 2017/1/12.
 */
public enum AuthenticationType {
    //不验证
    NoValidate,

    //验证普通权限
    User,

    //验证系统权限
    SystemUser;
}
