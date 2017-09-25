package com.imlib.kwchat;

import com.imlib.ChatMsg;
import com.imlib.kwchat.utils.EnterInfoUtil;
import com.imlib.kwchat.utils.MsgResolve;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yjdini on 2017/7/29.
 * 创建一个ChatSession实例会启动一个TCP连接，该连接的构造依赖userid与roomid
 * openSession() => 建立TCP连接
 * sendChatMsg()
 * getChatMsg() => 创建一个线程
 * close() => 断开连接，释放资源
 */
public class ChatSession implements ChatRoom {
    private static final String url = "60.29.226.5";
    private static final int port = 18133;
    private final Socket socket;
    private final Map<String, String> enterInfo;
    private final MessageReceiveAware msgAware;

    private final BufferedWriter outStream;
    private final BufferedReader inStream;

    protected Thread receiveThread;
    private boolean threadStopSign = false;
    protected List<ChatMsg> receiveMsg;


    public static ChatSession openSession(String userid, String websid, String roomid, MessageReceiveAware msgAware) {
        try {
            ChatSession instance = new ChatSession(userid, websid, roomid, msgAware);
            instance.connect();
            return instance;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class MessageReceiveAware {
        public void resolve(ChatMsg chatMsg){

        }
    }

    protected void connect() {
        String login = "id=1&sig={sig}&t={t}&channel=0&type=0&content=login:{chatid}:{chatname}\r";
        login = login.replace("{sig}", enterInfo.get("loginSig"))
                .replace("{t}", enterInfo.get("t"))
                .replace("{chatid}", enterInfo.get("chatid"))
                .replace("{chatname}", enterInfo.get("chatname"));
        String join = "id=2&sig={sig}&t={t}&channel={channel}&type=0&content=join:{chatid}\r";
        join = join.replace("{sig}", enterInfo.get("joinSig"))
                .replace("{t}", enterInfo.get("t"))
                .replace("{chatid}", enterInfo.get("chatid"))
                .replace("{channel}", enterInfo.get("channel"));
        sendTcpMsg(login);
        sendTcpMsg(join);
    }

    protected ChatSession(String userid, String websid, String roomid, MessageReceiveAware msgAware) throws IOException {
        this.msgAware = msgAware;
        socket = new Socket(url, port);
        outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "gbk"));
        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "gbk")) ;
        enterInfo = EnterInfoUtil.getEnterInfo(userid, websid, roomid);
        System.out.println("client start..");
    }

    private void destroy(){
        try {
            //再次确保所有资源被关闭
            outStream.close();
            inStream.close();
            socket.close();
            if (receiveThread != null)
                receiveThread.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    send tcp msg
     */
    private void sendTcpMsg(String toSend) {
        try {
            outStream.write(toSend);
            outStream.newLine();
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    send chat msg
     */
    @Override
    public void sendChatMsg(String msg){
        String toSend = "id=11&sig=&t=1&channel={channle}&type=1&content=0:{msg}\r";
        String channle = enterInfo.get("channel");
        toSend = toSend.replace("{channle}", channle)
                .replace("{msg}", msg);
        System.out.println("send:"+toSend);
        sendTcpMsg(toSend);
    }

    /**
     * ChatSession在实例化时并不具备接收消息的功能
     * 开启消息接收会创建一个线程，代价是昂贵的
     */
    protected void startListen() {
        if (receiveThread != null) {
            return;
        }
        receiveMsg = new ArrayList<ChatMsg>();
        receiveThread = new Thread() {
            public void run(){
                try {
                    String tcpMsg;
                    StringBuilder msgBuilder = new StringBuilder();
                    //将threadStopSign设置为true，线程会自动结束
                    while (!threadStopSign && (tcpMsg = inStream.readLine())!=null) {
                        try {
                            tcpMsg = tcpMsg.trim();
                            if (tcpMsg.contains("<resp") && tcpMsg.contains("</resp>")) {
                                ChatMsg chatMsg = MsgResolve.resolve(tcpMsg);
                                insertMsgToList(chatMsg);
                            } else if (tcpMsg.contains("<resp>")) {
                                msgBuilder.append(tcpMsg);
                            } else if (tcpMsg.contains("</resp>")) {
                                msgBuilder.append(tcpMsg);
                                ChatMsg chatMsg = MsgResolve.resolve(msgBuilder.toString());
                                insertMsgToList(chatMsg);
                                msgBuilder = new StringBuilder();
                            } else {
                                msgBuilder.append(tcpMsg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(tcpMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        receiveThread.start();
    }

    protected void insertMsgToList (ChatMsg chatMsg) {
        if (chatMsg == null)
            return;
        if (msgAware == null) {
            receiveMsg.add(chatMsg);
        } else {
            msgAware.resolve(chatMsg);
        }
    }

    /**
     * 获取消息前会判断该ChatSession是否在接收消息
     * 如果没有会先调用startListen方法
     * 获取消息时会将receiveMsg集合中的消息全部返回，然后清空该集合
     */
    @Override
    public List<ChatMsg> getChatMsg() {
        if (receiveThread == null) {
            startListen();
            return receiveMsg;
        }
        List<ChatMsg> re = receiveMsg;
        receiveMsg = new ArrayList<ChatMsg>();
        return re;
    }

    /**
     * 释放该Session的资源
     * 包括TCP连接
     * 可能包括用于接收消息而创建的线程
     */
    @Override
    public void close() {
        try {
            this.outStream.close();
            this.inStream.close();
            this.socket.close();
            this.threadStopSign = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        String userid = "393123771";
        String websid = "931985010";
        String roomid = "677613";

        ChatSession session = openSession(userid, websid, roomid, new MessageReceiveAware(){
            @Override
            public void resolve(ChatMsg chatMsg) {
                System.out.println(chatMsg);
            }
        });
        assert session != null;
        session.startListen();
        String msg;
        BufferedReader si = new BufferedReader(new InputStreamReader(System.in));
        while ((msg=si.readLine())!=null) {
            session.sendChatMsg(msg);
        }
    }
}
