package com.ini.entity;


import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "Skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;

    private Integer userId;

    private String tag;

    private String content;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
