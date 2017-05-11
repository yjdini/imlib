package com.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public class ResultMap {
    private final HashMap<String, Object> map;
    private Map result;
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

    public ResultMap result(Object object) {
        this.map.put("result", object);
        return this;
    }
    public ResultMap result(String key, String value) {
        if(result == null) {
            result = new HashMap<String, Object>();
            this.map.put("result", result);
        }
        result.put(key, value);
        return this;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }
}
