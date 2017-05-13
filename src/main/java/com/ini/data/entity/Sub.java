package com.ini.data.entity;


import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "Sub")
public class Sub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String token;

    private Integer status;

    public Sub(boolean initial) {
        if (initial) {
            createTime = new Date();
            status = 1;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        subId = subId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
