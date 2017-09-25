package com.imlib;

/**
 * Created by yjdini on 2017/8/3.
 * 只有received msg 才使用这个类
 * Concurrent message => 实时消息（没有发送时间字段）
 * Immutable =>不可变类
 * TODO 系统消息解析：礼物、进入房间、广播。
 *
 */
public final class ChatMsg {
    /*
    1 ：系统消息
    10：群发消息
    11：私聊消息
     */
    private final int type;
    private final String fromUserId;
    private final String fromUserName;
    private final String toUserId;
    private final String toUserName;
    private final String content;

    public ChatMsg(int type, String fromUserId, String fromUserName, String toUserId, String toUserName, String content) {
        this.type = type;
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
        this.content = content;
    }

    public String toString() {
        return "type:" + type +
                "; fromUserId:" + fromUserId +
                "; fromUserName:" + fromUserName +
                "; toUserId:" + toUserId +
                "; toUserName:" + toUserName +
                "; content:" + content;
    }

    public int getType() {
        return type;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public String getToUserId() {
        return toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public String getContent() {
        return content;
    }
}
