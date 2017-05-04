package com.ini.entity;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class Skill {
    private int id;
    private int subId;
    private int userId;
    private Date createTime;


    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
