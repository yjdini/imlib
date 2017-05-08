package com.ini.entity;


import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "Apply")
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;

    private Integer userId;

    private Integer result;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
