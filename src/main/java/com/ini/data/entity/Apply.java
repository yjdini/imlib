package com.ini.data.entity;


import javax.persistence.*;
import java.util.Date;


/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "Apply")
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applyId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private Integer userId;

    private String title;

    private String introduce;

    private String works;

    private String rejectReason;

    private String wechat;

    private String name;

    private Integer result;

    private Integer status;

    private String prize;

    private String phone;

    private String prove;

    private Integer subId;

    public Apply() {
    }

    public Apply(boolean initial) {
        if (initial) {
            this.setCreateTime(new Date());
            this.setStatus(1);
            this.setResult(0);
        }
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
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

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getProve() {
        return prove;
    }

    public void setProve(String prove) {
        this.prove = prove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
