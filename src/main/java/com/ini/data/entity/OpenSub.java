package com.ini.data.entity;


import javax.persistence.*;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "OpenSub")
public class OpenSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer openSubId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String schoolName;
    private String email;
    private String mpName;
    private Integer mpNum;
    private String wechat;

    private Integer status;// 1 审核中 ， 0 已完成
    private Integer result;// 0 待审核 ； 1 同意开通 ； 2 拒绝开通

    public OpenSub() {

    }
    public OpenSub(boolean initial) {
        if (initial) {
            this.setCreateTime(new Date());
            this.setStatus(1);
            this.result = 0;
        }
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }


    public Integer getOpenSubId() {
        return openSubId;
    }

    public void setOpenSubId(Integer openSubId) {
        this.openSubId = openSubId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMpName() {
        return mpName;
    }

    public void setMpName(String mpName) {
        this.mpName = mpName;
    }

    public Integer getMpNum() {
        return mpNum;
    }

    public void setMpNum(Integer mpNum) {
        this.mpNum = mpNum;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
