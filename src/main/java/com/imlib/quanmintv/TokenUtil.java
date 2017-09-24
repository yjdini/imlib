package com.imlib.quanmintv;

import com.imlib.utils.HttpUtil;
import com.imlib.utils.MD5Util;
import net.sf.json.JSONObject;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by yjdini on 2017/9/21.
 *
 */
public class TokenUtil {
    public final static String appId = "qmcc511acd0cbda3ee";
    public final static String appSecret = "568e77fae30ed1af8e38672196aed96c";
    public final static String cid = "33";

    public static String generateSign(TreeMap params) {
        Set<Map.Entry> entrys = params.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : entrys) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append(key).append('=').append(value).append('&');
        }
        sb.append("appSecret=").append(appSecret);
        return MD5Util.MD5(sb.toString()).toUpperCase();
    }

    public static String getToken() {
        String nonceStr = String.valueOf(System.currentTimeMillis()).substring(5);
        TreeMap params = new TreeMap();
        params.put("appId", appId);
        params.put("cid", cid);
        params.put("nonceStr", nonceStr);
        String sign = generateSign(params);

        String getTokenUrl = "http://open.quanmin.tv/app/get/token?appId={appId}" +
                "&cid={cid}&nonceStr={nonceStr}&sign={sign}";
        getTokenUrl = getTokenUrl.replace("{appId}",    appId);
        getTokenUrl = getTokenUrl.replace("{cid}",      cid);
        getTokenUrl = getTokenUrl.replace("{nonceStr}", nonceStr);
        getTokenUrl = getTokenUrl.replace("{sign}", sign);

        String resp = HttpUtil.get(getTokenUrl);
        JSONObject obj = JSONObject.fromObject(resp);
        JSONObject data = (JSONObject) obj.get("data");
        String token = data.getString("token");
        return token;
    }
}
