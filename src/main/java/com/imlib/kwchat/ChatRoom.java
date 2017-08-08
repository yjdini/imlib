package com.imlib.kwchat;

import java.util.List;

/**
 * Created by yjdini on 2017/8/3.
 *
 */
public interface ChatRoom {
    void sendChatMsg(String toSend);
//    void sendChatMsg(String toSend, String toUserId); TODO 指定用户发送消息

    /**
     * 结束时必须要调用，释放tcp资源，thread资源
     */
    void close();

    /**
     * Tcp消息源获取到的消息是没有时间戳的
     * 接收到的所有消息均为实时消息，发送时间以当前时间为准
     * 该接口不提供获取历史消息功能
     */
    List<ChatMsg> getChatMsg();
}
