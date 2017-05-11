package com.ini.aop.authentication;

/**
 * Created by Somnus`L on 2017/1/12.
 */
public enum AuthenticationType {
    //不验证
    NoValidate,

    //普通用户
    CommonUser,

    //行家用户
    Master,

    //分站管理员
    Admin,

    //超级管理员
    Root


}
