package com.ini.dao.entity;


import javax.persistence.*;

import org.hibernate.Interceptor;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;

    private String nickname;

    private String name;

    private String avatar;

    private String studentCard;

    private String password;

    private Integer grade;

    private Integer status;

    private char type;

    private String major;

    private String phone;

    private String wechart;

    private String introduce;

    private String title;

    private String works;

    private Integer orderTimes;

    private Integer orderedTimes;

    private BigDecimal score;

    private Integer subId;


    public User() {
        this.setCreateTime(new DateTime());
        this.setOrderedTimes(0);
        this.setOrderTimes(0);
        this.setStatus(1);
        this.setType('c');
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(String studentCard) {
        this.studentCard = studentCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public char getType() {
        return type;
    }

    /**
     * 'm'aster; 'c'ommon
     * @param type
     */
    public void setType(char type) {
        this.type = type;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechart() {
        return wechart;
    }

    public void setWechart(String wechart) {
        this.wechart = wechart;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public Integer getOrderTimes() {
        return orderTimes;
    }

    public void setOrderTimes(Integer orderTimes) {
        this.orderTimes = orderTimes;
    }

    public Integer getOrderedTimes() {
        return orderedTimes;
    }

    public void setOrderedTimes(Integer orderedTimes) {
        this.orderedTimes = orderedTimes;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }
}
