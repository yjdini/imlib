package com.ini.data.schema;

import com.ini.data.entity.Orders;
import com.ini.data.entity.User;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */

public class CommentUserSet {
    private Date createTime;
    private String content;
    private Integer score;

    private String avatar;
    private Integer userId;
    private String userName;
    private String nickName;
    private String userType;
    private String title;

    public CommentUserSet(Orders order, User user) {
        this.createTime = order.getCommentTime();
        this.content = order.getComment();
        this.score = order.getScore();
        this.avatar = user.getAvatar();
        this.userId = user.getUserId();
        this.userName = user.getName();
        this.nickName = user.getNickname();
        this.userType = user.getType();
        this.title = user.getTitle();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
