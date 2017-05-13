package com.ini.data.entity;


import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "OpenSub")
public class OpenSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer OpenSubId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String schoolName;
    private String email;
    private String mpName;
    private Integer mpNum;
    private String wechat;
    private String phone;

    private Integer status;

    public OpenSub() {

    }
    public OpenSub(boolean initial) {
        if (initial) {
            this.setCreateTime(new Date());
            this.setStatus(1);
        }
    }

    public Integer getOpenSubId() {
        return OpenSubId;
    }

    public void setOpenSubId(Integer openSubId) {
        OpenSubId = openSubId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
