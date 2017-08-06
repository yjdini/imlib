package com.imlib.kwchat.utils;

import com.imlib.kwchat.ChatMsg;
import com.imlib.kwchat.ChatSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjdini on 2017/8/4.
 *
 * 重写了存储chatMsg的方式
 * 重写了获取消息的逻辑，使得消息可以被多次接收，而不是接收一次后就清空receiveMsg
 *
 * 提供了一个方法=>getChatMsg(int index):使得receiveMsg list可以被随机&重复读取。
 */
public class ReceiveReusedChatSession extends ChatSession {
    /*
    将receiveMsg作为一个循环列表使用
    指针insertIndex表示input流当前输入的位置
    指针getIndex表示下一次调用getChatMsg输出的第一个msg的位置,一般由调用方提供，不使用这个变量
    listSize表示该列表的长度，要足够大到可以容纳单位时间间隔内的所有消息，否则会出现获取消息错位
    ChatMsg是不可变类，在返回ChatMsg list时，不需要安全性拷贝
     */
    public final static int listSize = 100;

    private int insertIndex = 0;

    private int getIndex = 0;

    private ReceiveReusedChatSession(String userid, String websid, String roomid) throws IOException {
        super(userid, websid, roomid);
    }

    /**
     * 构造逻辑与ChatSession一致
     */
    public static ReceiveReusedChatSession openSession(String userid, String websid, String roomid) {
        try {
            ReceiveReusedChatSession instance = new ReceiveReusedChatSession(userid, websid, roomid);
            instance.connect();
            return instance;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 重写接收到的ChatMsg存储逻辑
     */
    @Override
    protected void insertMsgToList(ChatMsg chatMsg) {
        if (chatMsg == null)
            return;
        if (receiveMsg.size() < listSize) {
            receiveMsg.add(chatMsg);
        } else {//receiveMsg.size() == listSize
            receiveMsg.set(insertIndex, chatMsg);
        }
        //insertIndex指针向前移一格 范围为 0 ~ listSize-1
        insertIndex = (insertIndex + 1)%100;
    }

    /**
     * 重写获取ChatMsg的逻辑
     */
    @Override
    public List<ChatMsg> getChatMsg() {
        if (receiveThread == null) {
            startListen();
            return null;
        }
        List<ChatMsg> msgs = new ArrayList<ChatMsg>();
        int start = getIndex, end = insertIndex;
        while (start != end) {
            msgs.add(receiveMsg.get(start));
            start = (start + 1)%100;
        }
        getIndex = end;
        return msgs;
    }

    /**
     * 消息随机读取的方法
     * 不同的调用方需要自己保存一个最近一次接收消息的index，通过该index值来读取各自的接收消息
     * 这使得多个调用方可以共用一个MsgReusedChatSession对象作为消息源
     */
    public List<ChatMsg> getChatMsg(int index) {
        if (receiveThread == null) {
            startListen();
            return null;
        }
        List<ChatMsg> msgs = new ArrayList<ChatMsg>();
        int start = index, end = insertIndex;
        while (start != end) {
            msgs.add(receiveMsg.get(start));
            start = (start + 1)%100;
        }
        return msgs;
    }

    public int getInsertIndex() {
        return insertIndex;
    }
}
