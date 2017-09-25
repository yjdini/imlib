package com.imlib.quanmintv;

import org.msgpack.MessagePack;
import org.msgpack.type.IntegerValue;
import org.msgpack.type.MapValue;
import org.msgpack.type.Value;
import org.msgpack.type.ValueFactory;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.CRC32;

/**
 * Created by yjdini on 2017/9/21.
 *
 */
public class SocketClient {
    private static SocketClient socketClient; // singleton实例
    private final MsgBuilder msgBuilder = new MsgBuilder();

    private static final String ip = "101.132.85.192";
    private static final int port = 8700;

    private final Socket socket;
    private final OutputStream out;
    private final InputStream in;

    private final Thread msgListener;
    private static boolean listenerStopSign = false;
    private Timer heartBeatTimer;

    private final MessageReceiveAware messageReceiveAware ;

    public static void main(String args[]) throws IOException {
        SocketClient socketClient = SocketClient.getInstance(new MessageReceiveAware(){
            protected void resolve(Value value, int type){
                System.out.println(value);
                System.out.println(type);
            }
        });
        Integer i = null;
        socketClient.subscribe(new Integer[]{29105,8832533});
//        socketClient.unSubscribe(4212632);
//        socketClient.close();
    }

    public static SocketClient getInstance(MessageReceiveAware messageReceiveAware) throws IOException {
        if (socketClient == null) {
            socketClient = new SocketClient(messageReceiveAware);
            socketClient.validateSession();
            socketClient.sendHeartBeat();
        }
        return socketClient;
    }

    public void subscribe(Integer[] owids) throws IOException {
        out.write(msgBuilder.buildReqS1002(owids));
    }

    public void unSubscribe(Integer owid) throws IOException {
        out.write(msgBuilder.buildReqS1003(owid));
    }

    public void close() throws IOException {
        listenerStopSign = true;
        heartBeatTimer.cancel();
        out.close();
        in.close();
        socket.close();
        socketClient = null;
    }

    private SocketClient(MessageReceiveAware messageReceiveAware) throws IOException {
        socket = new Socket(ip, port);

        out = socket.getOutputStream();
        in = socket.getInputStream();
        this.messageReceiveAware = messageReceiveAware;

        msgListener = new Thread() {
            public void run() {
                MessagePack pack = new MessagePack();
                byte[] msgBuffer = new byte[10000];
                listenerStopSign = false;
                while (!listenerStopSign) {
                    try {
                        int len = 0;
                        len = in.read(msgBuffer);
                        if (len >= 24) {
                            byte[] respHeader = new byte[24];
                            byte[] respBody = new byte[len - 24];
                            System.arraycopy(msgBuffer, 0, respHeader, 0, 24);
                            System.arraycopy(msgBuffer, 24, respBody, 0, len - 24);
                            messageReceiveAware.resolve(pack.read(respBody), BytesUtil.bytesToInteger(respHeader, 8));
                        }
                    } catch (IOException e) {
                    }
                }
            }
        };
        msgListener.start();
    }

    private void validateSession() throws IOException {
        out.write(msgBuilder.buildReqS1001(TokenUtil.appId, TokenUtil.getToken()));
    }

    private void sendHeartBeat(){
        if (socketClient == null) {
            return;
        }
        if(heartBeatTimer != null){
            heartBeatTimer.cancel();
        }

        heartBeatTimer = new Timer();
        heartBeatTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    out.write(msgBuilder.buildReqS1004(TokenUtil.appId, TokenUtil.getToken()));
                } catch (Exception e) {
                    sendHeartBeat();
                }
            }

        },0,1000*50);
    }

    class MsgBuilder {
        byte[] buildReqS1001(String appId, String token) throws IOException {
            MessagePack pack = new MessagePack();
            Value[] values = new Value[4];
            values[0] = ValueFactory.createRawValue("appId".getBytes());
            values[1] = ValueFactory.createRawValue(appId.getBytes());
            values[2] = ValueFactory.createRawValue("token".getBytes());
            values[3] = ValueFactory.createRawValue(token.getBytes());
            MapValue mv = ValueFactory.createMapValue(values);
            byte[] body = pack.write(mv);

            long crc = getCrc32(body);
            byte[] header = buildHeader(20 + body.length, 1, 1001, 2, crc, 0);

            byte[] req = new byte[24+body.length];
            System.arraycopy(header, 0, req, 0, 24);
            System.arraycopy(body, 0, req, 24, body.length);

            return req;
        }

        byte[] buildReqS1002(Integer[] owids) throws IOException {
            MessagePack pack = new MessagePack();
            Value[] values = new Value[2];

            values[0] = ValueFactory.createRawValue("owid".getBytes());
            Value[] ids = new Value[owids.length];
            for (int i = 0; i < owids.length; i ++) {
                ids[i] = ValueFactory.createIntegerValue(owids[i]);
            }
            values[1] = ValueFactory.createArrayValue(ids);
            MapValue mv = ValueFactory.createMapValue(values);
            byte[] body = pack.write(mv);

            //
            Value[] vs = new Value[2];

            vs[0] = ValueFactory.createRawValue("owid".getBytes());
            vs[1] = ValueFactory.createArrayValue(new IntegerValue[]{ValueFactory.createIntegerValue(123)});
            MapValue test = ValueFactory.createMapValue(vs);


            long crc = getCrc32(body);
            byte[] header = buildHeader(20 + body.length, 1, 1002, 2, crc, 0);

            byte[] req = new byte[24+body.length];
            System.arraycopy(header, 0, req, 0, 24);
            System.arraycopy(body, 0, req, 24, body.length);

            return req;
        }

        byte[] buildReqS1003(Integer owid) throws IOException {
            MessagePack pack = new MessagePack();
            Value[] values = new Value[2];
            values[0] = ValueFactory.createRawValue("owid".getBytes());
            values[1] = ValueFactory.createArrayValue(new IntegerValue[]{ValueFactory.createIntegerValue(owid)});
            MapValue mv = ValueFactory.createMapValue(values);
            byte[] body = pack.write(mv);

            long crc = getCrc32(body);
            byte[] header = buildHeader(20 + body.length, 1, 1003, 2, crc, 0);

            byte[] req = new byte[24+body.length];
            System.arraycopy(header, 0, req, 0, 24);
            System.arraycopy(body, 0, req, 24, body.length);

            return req;
        }

        byte[] buildReqS1004(String appId, String token) throws IOException {
            return buildHeader(20, 1, 1004, 2, 0, 0);
        }

        byte[] buildHeader(int length, int sequence, int type, int ver, long crc, int reserve) {
            byte[] header = new byte[24];
            System.arraycopy(BytesUtil.intToBytes(length), 0, header, 0, 4);
            System.arraycopy(BytesUtil.intToBytes(sequence), 0, header, 4, 4);
            System.arraycopy(BytesUtil.intToBytes(type), 0, header, 8, 4);
            System.arraycopy(BytesUtil.intToBytes(ver), 0, header, 12, 4);
            System.arraycopy(BytesUtil.longToBytes(crc), 0, header, 16, 4);
            System.arraycopy(BytesUtil.intToBytes(reserve), 0, header, 20, 4);
            return header;
        }

        long getCrc32(byte[] bytes) {
            CRC32 crc32 = new CRC32();
            crc32.update(bytes);
            return crc32.getValue();
        }
    }


    public static class MessageReceiveAware {
        protected void resolve(Value value, int type){
            System.out.println(value);
        }
    }
}

//
//@Message
//class S1001 implements Serializable {
//    private static final long serialVersionUID = -458914520933183052L;
//
//    private String appId;
//    private String token;
//
//    public String getAppId() {
//        return appId;
//    }
//
//    public void setAppId(String appId) {
//        this.appId = appId;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    S1001(String appId, String token) {
//        this.appId = appId;
//        this.token = token;
//    }
//
//    public S1001() {
//    }
//}
