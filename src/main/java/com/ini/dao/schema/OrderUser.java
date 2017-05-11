package com.ini.dao.schema;

import com.ini.dao.entity.Orders;
import com.ini.dao.entity.Skill;
import com.ini.dao.entity.Tag;
import com.ini.dao.entity.User;

import java.math.BigDecimal;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public class OrderUser {
    private Integer fromUserId;
    private Integer toUserId;
    private String orderIntroduction;
    private Integer skillId;
    private String toUserName;
    private String fromUserName;
    private String toUserNickname;
    private String fromUserNickname;
    private String toUserWeChat;
    private String fromUserWeChat;
    private Integer result;
    private String rejectReason;
    private Integer isCommented;
    private Integer orderId;
    private String fromUserAvatar;
    private String toUserAvatar;
    private String skillTagName;
    private String skillTitle;
    private BigDecimal skillScore;

    public OrderUser(Orders order, User to, User from, Tag tag, Skill skill) {
        this.fromUserId = from.getUserId();
        this.toUserId = to.getUserId();
        this.orderIntroduction = order.getIntroduction();
        this.skillId = skill.getSkillId();
        this.toUserName = to.getName();
        this.fromUserName = from.getName();
        this.fromUserNickname = from.getNickname();
        this.toUserNickname = to.getNickname();
        this.toUserWeChat = to.getWechat();
        this.fromUserWeChat = from.getWechat();
        this.result = order.getResult();
        this.rejectReason = order.getRejectReason();
        this.isCommented = order.getIsCommented();
        this.orderId = order.getOrderId();
        this.fromUserAvatar = from.getAvatar();
        this.toUserAvatar = to.getAvatar();
        this.skillTagName = tag.getName();
        this.skillTitle = skill.getTitle();
        this.skillScore = skill.getScore();
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getOrderIntroduction() {
        return orderIntroduction;
    }

    public void setOrderIntroduction(String orderIntroduction) {
        this.orderIntroduction = orderIntroduction;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserNickname() {
        return toUserNickname;
    }

    public void setToUserNickname(String toUserNickname) {
        this.toUserNickname = toUserNickname;
    }

    public String getFromUserNickname() {
        return fromUserNickname;
    }

    public void setFromUserNickname(String fromUserNickname) {
        this.fromUserNickname = fromUserNickname;
    }

    public String getToUserWeChat() {
        return toUserWeChat;
    }

    public void setToUserWeChat(String toUserWeChat) {
        this.toUserWeChat = toUserWeChat;
    }

    public String getFromUserWeChat() {
        return fromUserWeChat;
    }

    public void setFromUserWeChat(String fromUserWeChat) {
        this.fromUserWeChat = fromUserWeChat;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getIsCommented() {
        return isCommented;
    }

    public void setIsCommented(Integer isCommented) {
        this.isCommented = isCommented;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getFromUserAvatar() {
        return fromUserAvatar;
    }

    public void setFromUserAvatar(String fromUserAvatar) {
        this.fromUserAvatar = fromUserAvatar;
    }

    public String getToUserAvatar() {
        return toUserAvatar;
    }

    public void setToUserAvatar(String toUserAvatar) {
        this.toUserAvatar = toUserAvatar;
    }

    public String getSkillTagName() {
        return skillTagName;
    }

    public void setSkillTagName(String skillTagName) {
        this.skillTagName = skillTagName;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }

    public BigDecimal getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(BigDecimal skillScore) {
        this.skillScore = skillScore;
    }
}
