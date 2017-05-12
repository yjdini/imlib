package com.ini.data.schema;

import com.ini.data.entity.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/12.
 */
public class UserSet {
    private Integer userId;

    private Date createTime;

    private String nickname;

    private String name;

    private String avatar;

    private String studentCard;

    private Integer grade;

    private Integer status;

    private String type;

    private String major;

    private String phone;

    private String wechat;

    private String introduce;

    private String title;

    private String works;

    private Integer orderTimes;

    private Integer orderedTimes;

    private BigDecimal score;

    private Integer subId;

    public UserSet(User user) {
        this.userId = user.getUserId();
        this.createTime = user.getCreateTime();
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.avatar = user.getAvatar();
        this.studentCard = user.getStudentCard();
        this.grade = user.getGrade();
        this.status = user.getStatus();
        this.type = user.getType();
        this.major = user.getMajor();
        this.phone = user.getPhone();
        this.wechat = user.getWechat();
        this.introduce = user.getIntroduce();
        this.title = user.getTitle();
        this.works = user.getWorks();
        this.orderTimes = user.getOrderTimes();
        this.orderedTimes = user.getOrderedTimes();
        this.score = user.getScore();
        this.subId = user.getSubId();
    }
}
