package com.ini.data.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/4.
 */

@SqlResultSetMapping
(
    name="SkillUserEntity",
    entities = {
        @EntityResult
        (
            entityClass = com.ini.data.entity.Skill.class
        ),
        @EntityResult
        (
            entityClass = com.ini.data.entity.User.class
        )
    },
    columns = {
        @ColumnResult(name="tagname")
    }
)
@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private Integer userId;

    private String title;

    private String totalPrice;

    private String totalTime;

    private Integer orderTimes;

    private Integer orderedTimes;

    private String works;

    private Integer tagId;

    private String description;

    private BigDecimal score;

    private Integer status;

    private Integer subId;

    private Integer showTimes;

    private Integer seoScore;

    public Integer getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(Integer showTimes) {
        this.showTimes = showTimes;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Skill() {

    }

    public Skill(boolean initial) {
        this.setCreateTime(new Date());
        this.setStatus(1);
        this.setOrderedTimes(0);
        this.setOrderTimes(0);
        this.setShowTimes(0);
        this.setSeoScore(0);
        this.setOrderTimes(0);
        this.setOrderedTimes(0);
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getOrderTimes() {
        return orderTimes;
    }

    public void setOrderTimes(Integer orderTimes) {
        this.orderTimes = orderTimes;
    }

    public Integer getSeoScore() {
        return seoScore;
    }

    public void setSeoScore(Integer seoScore) {
        this.seoScore = seoScore;
    }

    public Integer getOrderedTimes() {
        return orderedTimes;
    }

    public void setOrderedTimes(Integer orderedTimes) {
        this.orderedTimes = orderedTimes;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
