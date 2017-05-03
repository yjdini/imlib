package com.ini.entity;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class Comment {
    private int fromUser;
    private int toUser;
    private String content;
    private Date createTime;

    public int getFromUser() {
        return fromUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }
}
