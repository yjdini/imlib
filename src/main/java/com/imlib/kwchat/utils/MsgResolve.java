package com.imlib.kwchat.utils;

import com.imlib.kwchat.ChatMsg;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLDecoder;

/**
 * Created by yjdini on 2017/8/3.
 *
 */
public class MsgResolve {
    public static ChatMsg resolve(String tcpMsg) {
        try {
            Document document = DocumentHelper.parseText(tcpMsg);
            Element root = document.getRootElement();

            String fromUid = root.attribute("f").getText();
            if ("0".equals(fromUid)) {//login，join的返回
                return null;
            } else if ("999999991".equals(fromUid)) {//系统消息、系统广播（暂时不作解析）
                return new ChatMsg(1, null, null,
                        null, null, root.getText());
            } else {
                String[] tmp;
                String tmp2;
                String n = root.attribute("n").getText();
                tmp = n.split("_");
                String fromUserName = URLDecoder.decode(tmp[tmp.length - 1]);
                String content, toUserName = null, toUId = null;
                int type;
                tmp = root.getText().split("\\|");// => "|"
                content = tmp[1];
                if ("#".equals(tmp[0])) { //群发消息
                    type = 10;
                } else { //私聊消息
                    type = 11;
                    tmp = tmp[0].split("_");
                    toUId= tmp[0];
                    toUserName = tmp[tmp.length - 1];
//                    toUserName = toUserName.substring(0, toUserName.length() - 1);
                    toUserName = URLDecoder.decode(toUserName);
                }
                return new ChatMsg(type, fromUid, fromUserName,
                        toUId, toUserName, content);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        //<resp c="167763679" t="1" f="77486675679" n="0___%E7%89%B5%E6%89%8B%EF%BC%8C%E7%9A%84%E6%B5%AA%E6%BC%AB" ext="eyJ1MSI6eyJwIjozLCJyIjowfX0K" >#|李小璐唉</resp>
        //<resp c="167763679" t="1" f="34743944256" n="2___%E6%9C%A6%E8%83%A7%E6%9A%96%E5%BF%83" ext="eyJ1MSI6eyJwIjozLCJyIjo3LCJzdCI6Mn0sInUyIjp7InAiOjMsInIiOjB9fQo=" >77486675679_0___%E7%89%B5%E6%89%8B%EF%BC%8C%E7%9A%84%E6%B5%AA%E6%BC%AB| 还真是</resp>
        String xml = "<resp c=\"167763679\" t=\"0\" f=\"999999991\" n=\"\" ext=\"eyJ1MSI6eyJiZCI6IiIsImlkdCI6MCwibm4iOiIlRTYlODIlQTYlRUYlQkMlOEMlRTUlQjAlOEYlRTUlODUlOTQiLCJwIjozLCJyIjowLCJyaWQiOjY3MTY3NX19Cg==\" ><![CDATA[cmd=notifyaudience\n" +
                "userlist{\n" +
                "type=1\n" +
                "}\n" +
                "userlist{\n" +
                "type=2\n" +
                "user{\n" +
                "id=392865845\n" +
                "}\n" +
                "}\n" +
                "]]></resp>";
        ChatMsg msg = resolve(xml);
        if (msg == null) return;
        System.out.println("type："+msg.getType());
        System.out.println("toName："+msg.getToUserName());
        System.out.println("toId："+msg.getToUserId());
        System.out.println("fName："+msg.getFromUserName());
        System.out.println("fId："+msg.getFromUserId());
        System.out.println("content："+msg.getContent());

    }
}
