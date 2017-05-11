package com.utils;

import java.util.HashMap;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public class ResultMap {
    private final HashMap<String, Object> map;
    private ConstJson.Result id;
    /**
     * static factory method
     * @return
     */
    public static ResultMap ok() {
        ResultMap resultMap = new ResultMap();
        resultMap.map.put("status", "ok");
        return resultMap;
    }

    public static ResultMap error() {
        ResultMap resultMap = new ResultMap();
        resultMap.map.put("status", "error");
        return resultMap;
    }

    public static ResultMap unlogin() {
        ResultMap resultMap = new ResultMap();
        resultMap.map.put("status", "unlogin");
        return resultMap;
    }

    private ResultMap() {
        this.map = new HashMap<String, Object>();
    }

    public ResultMap setMessage(String message) {
        this.map.put("message", message);
        return this;
    }

    public ResultMap put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }
}
