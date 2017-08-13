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

    public static Map<String,String> getEnterInfo(String userid, String websid, String roomid){
        String resp = HttpUtil.get("http://zhiboserver.kuwo.cn/proxy.p?src=android_jx&cmd=enterroom&uid="+userid+"&sid="+websid+"&rid="+roomid+"&secrectname=%E5%85%A8%E7%BA%BF%E5%BC%80%E6%92%AD%E5%8A%A9%E6%89%8B&from=6500100022&macid=224192608&appversion=kwlive_ar_3.5.3.0&plat=1&token=a0a641efa116962430d60e6de65fa0a3&logintype=1");
//        int start = resp.indexOf("var enterInfo = ");
//        int end = resp.indexOf("var tongjiDate2");
//        String re = resp.substring(start + 16, end).trim();
//        re = re.substring(0, re.length() -1);
        System.out.println(resp);
        //        com.alibaba.fastjson.JSONObject jsobj = com.alibaba.fastjson.JSONObject.parseObject(resp);
        JSONObject jsobj = JSONObject.fromObject(resp);

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


    /**
     * websid:98973447 <= sid=98973447_1
     * @return
     */
    public static Map<String,String> getEnterInfo2(String userid, String websid, String roomid) {
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
