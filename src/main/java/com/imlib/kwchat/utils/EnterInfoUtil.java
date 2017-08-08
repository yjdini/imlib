package com.imlib.kwchat.utils;

import net.sf.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yjdini on 2017/8/2.
 *
 */
public class EnterInfoUtil {
    /**
     * websid:98973447 <= sid=98973447_1
     * @return
     */
    public static Map<String,String> getEnterInfo(String userid, String websid, String roomid) {
        Map header = new HashMap();
        header.put("Cookie", "userid=" + userid + ";websid=" + websid + ";");
        String resp = HttpUtil.get("http://jx.kuwo.cn/" + roomid, header);
        int start = resp.indexOf("var enterInfo = ");
        int end = resp.indexOf("var tongjiDate2");
        String re = resp.substring(start + 16, end).trim();
        re = re.substring(0, re.length() -1);
        JSONObject jsobj = JSONObject.fromObject(re);

        String chatid = jsobj.getString("chatid");
        String t = jsobj.getJSONObject("chatroom")
                .get("tm") + "";
        String joinSig = jsobj.getJSONObject("chatroom")
                .getJSONObject("channel")
                .getJSONObject("join")
                .get("sig") + "";
        String loginSig = jsobj.getJSONObject("chatroom")
                .getJSONObject("channel")
                .getJSONObject("login")
                .get("sig") + "";
        String channel = jsobj.getJSONObject("chatroom")
                .getJSONObject("channel")
                .getJSONObject("join")
                .get("id") + "";
        String chatname = jsobj.getJSONObject("chatroom")
                .getJSONObject("channel")
                .getJSONObject("login")
                .getString("chatname");
        chatname = URLEncoder.encode(chatname);

        Map map = new HashMap<String, String>();
        map.put("chatid", chatid);
        map.put("t", t);
        map.put("joinSig", joinSig);
        map.put("loginSig", loginSig);
        map.put("channel", channel);
        map.put("chatname", chatname);
        return map;
    }
}
