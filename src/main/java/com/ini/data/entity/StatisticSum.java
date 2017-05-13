package com.ini.data.entity;


import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@Entity
@Table(name = "StatisticSum")
public class StatisticSum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer statisticSumId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private Integer user;
    private Integer master;
    private Integer skill;
    private Integer orders;
    private Integer finishOrder;

    public Integer getStatisticSumId() {
        return statisticSumId;
    }

    public void setStatisticSumId(Integer statisticSumId) {
        this.statisticSumId = statisticSumId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    public Integer getSkill() {
        return skill;
    }

    public void setSkill(Integer skill) {
        this.skill = skill;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getFinishOrder() {
        return finishOrder;
    }

    public void setFinishOrder(Integer finishOrder) {
        this.finishOrder = finishOrder;
    }
}
