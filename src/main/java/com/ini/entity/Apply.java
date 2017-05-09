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
    private Integer applyId;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;

    private Integer userId;

    private String title;

    private String introduce;

    private String works;

    private String rejectReason;

    private String wechart;

    private Integer result;

    private Integer status;

    public Apply() {
        this.setCreateTime(new DateTime());
        this.setStatus(1);
        this.setResult(0);
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getWechart() {
        return wechart;
    }

    public void setWechart(String wechart) {
        this.wechart = wechart;
    }

    public Integer getResult() {
        return result;
    }

    /**
     * 0:审核中； 1：通过； 2：拒绝；
     * @param result
     */
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
