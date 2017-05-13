package com.ini.data.entity;


import javax.persistence.*;
import java.util.Date;


/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "Tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String name;

    public Tag() {
    }

    public Tag(boolean initial) {
        this.setCreateTime(new Date());
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
