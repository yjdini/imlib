package com.imlib.kwchat;

import com.imlib.ChatMsg;
import com.imlib.kwchat.utils.ReceiveReusedChatSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yjdini on 2017/8/4.
 *
 * 该类对外提供的接口、对外表现和ChatSession类完全一致，只是内部实现不同，开销更低
 * 假设系统中共有m个user分布在n个room中（m>n）
 * ChatSession需要实例化m次，会创建m个线程、m个TCP连接=>1个user对应1个ChatSession
 * ReusedChatSession：
 * 在接收消息上会创建n个线程、n个TCP连接=>1个room对应1个ChatSession，所有在该room中的user共用一个ChatSession
 * 发送消息时，ReusedChatSession会为该用户创建一个一次性的ChatSession用于发送消息，发送完成后即销毁该ChatSession。
 *
 * ReusedChatSession的实现使用适配器模式，它内部保存着一个ChatSession子类对象的引用，来对外提供同样的接口。
 * openSession()=>sendChatMsg() || getChatMsg();
 * ReusedChatSession的稳定性是建立在 ChatSession连接稳定且不会失效的假设上的，TODO 后续要为ReusedChatSession类添加断线重连的功能
 */
public class ReusedChatSession implements ChatRoom{
    //room => ReceiveReusedChatSession 全局对象需要进行读写同步
    private final static Map<String, ReceiveReusedChatSession> sessions = new ConcurrentHashMap<String, ReceiveReusedChatSession>();

    //room => 被引用的次数。当引用次数为0时，说明该房间没有用户在了，会移除sessions中该room对应的ChatSession，并释放资源
    private final static Map<String, Integer> referTimes = new ConcurrentHashMap<String, Integer>();

    private final static int listSize = ReceiveReusedChatSession.listSize;

    private final String userid;
    private final String websid;
    private final String roomid;
    private int getMsgIndex;


    public static ReusedChatSession openSession(String userid, String websid, String roomid) {
        return new ReusedChatSession(userid, websid, roomid);
    }

    private ReusedChatSession(String userid, String websid, String roomid) {
        this.userid = userid;
        this.websid = websid;
        this.roomid = roomid;

        ReceiveReusedChatSession session = ReceiveReusedChatSession.openSession(userid, websid, roomid);

        assert session != null;

//        referTimes.

        if (referTimes.get(roomid) == null || referTimes.get(roomid) == 0) { //该房间在sessions中不存在，需要缓存一个ChatSession
            referTimes.put(roomid, 1);
            sessions.put(roomid, session);
            getMsgIndex = 0;
            session.startListen();
        } else {//该房间已经在sessions中存在，可重用
            referTimes.put(roomid, referTimes.get(roomid) + 1);
            getMsgIndex = session.getInsertIndex();
            //创建session再关掉是为了系统显示消息：xxx进入了房间
            session.close();
        }
    }

    @Override
    public void sendChatMsg(String toSend) {
        ReceiveReusedChatSession session = ReceiveReusedChatSession.openSession(userid, websid, roomid);
        session.sendChatMsg(toSend);
        session.close();
    }

    @Override
    public void close() {
        //关闭ChatSession
        ReceiveReusedChatSession session = sessions.get(roomid);
        if (session != null) {
            session.close();
        }

        //refer --
        Integer refertime = referTimes.get(roomid);
        if (refertime != null && refertime <= 1){//该用户为这个房间的最后一个用户
            referTimes.put(roomid, null);
            sessions.put(roomid, null);
        } else if (refertime != null) {
            referTimes.put(roomid, referTimes.get(roomid) - 1);
        }
    }

    @Override
    public List<ChatMsg> getChatMsg() {
        List<ChatMsg> re = sessions.get(roomid).getChatMsg(getMsgIndex);
        int msgAmount = re.size();
        //指针前移msgAmount格
        getMsgIndex = (getMsgIndex + msgAmount) % listSize;
        return re;
    }
}
