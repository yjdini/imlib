package com.ini.data.schema;

import com.ini.data.entity.Apply;
import com.ini.data.entity.User;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/13.
 *
 */
public class ApplyUserSet {
    private Integer applyId;

    private Date createTime;

    private Integer userId;

    private String title;

    private String wechat;

    private String name;

    private Integer result;

    private String avatar;

    private String nickname;

    private String userType;

    public ApplyUserSet(Apply apply, User user) {
        this.avatar = user.getAvatar();
        this.applyId = apply.getApplyId();
        this.createTime = apply.getCreateTime();
        this.userId = apply.getUserId();
        this.title = apply.getTitle();
        this.wechat = apply.getWechat();
        this.result = apply.getResult();
        this.name = apply.getName();
        this.nickname = user.getNickname();
        this.userType = user.getType();
    }

    public Integer getApplyId() {
        return applyId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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


    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
