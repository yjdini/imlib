package com.ini.dao.schema;

import com.ini.dao.entity.Skill;
import com.ini.dao.entity.Tag;
import com.ini.dao.entity.User;

import java.math.BigDecimal;

/**
 * Created by Somnus`L on 2017/5/10.
 *
 */
public class SkillUserTagSet {

    private Integer skillId;
    private Integer skillOrderTimes;
    private Integer skillOrderedTimes;
    private String totalTime;
    private String totalPrice;
    private BigDecimal skillScore;
    private String description;
    private String skillTitle;

    private Integer userId;
    private String userTitle;
    private String nickName;
    private BigDecimal userScore;
    private String userName;
    private String avatar;

    private String tagName;

    public SkillUserTagSet(Skill skill, User user, Tag tag) {
        this.skillId = skill.getSkillId();
        this.skillOrderTimes = skill.getOrderedTimes();
        this.skillOrderedTimes = skill.getOrderedTimes();
        this.totalTime = skill.getTotalTime();
        this.totalPrice = skill.getTotalPrice();
        this.skillScore = skill.getScore();
        this.description = skill.getDescription();
        this.skillTitle = skill.getTitle();
        this.userId = user.getUserId();
        this.userTitle = user.getTitle();
        this.nickName = user.getNickname();
        this.userName = user.getName();
        this.userScore = user.getScore();
        this.avatar = user.getAvatar();
        this.tagName = tag.getName();
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getSkillOrderTimes() {
        return skillOrderTimes;
    }

    public void setSkillOrderTimes(Integer skillOrderTimes) {
        this.skillOrderTimes = skillOrderTimes;
    }

    public Integer getSkillOrderedTimes() {
        return skillOrderedTimes;
    }

    public void setSkillOrderedTimes(Integer skillOrderedTimes) {
        this.skillOrderedTimes = skillOrderedTimes;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(BigDecimal skillScore) {
        this.skillScore = skillScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public BigDecimal getUserScore() {
        return userScore;
    }

    public void setUserScore(BigDecimal userScore) {
        this.userScore = userScore;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
