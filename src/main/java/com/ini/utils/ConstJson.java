package com.ini.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/4.
 * dont use it
 * replace it with ResultMap
 */
public class ConstJson {
    public final static Result OK = new Result("ok");
    public final static Result UNLOGIN = new Result("unlogin");
    public final static Result ERROR = new Result("error");
    public final static Result UNREGIST = new Result("unRegist");
    public final static Result PSDERR = new Result("psdErr");

    public static class Result {
        private String status;
        private String message;
        private Map result;
        public Result(String status) {
            this.status = status;
        }

        public Result(String status, String message, Map result) {
            this.status = status;
            this.result = result;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public <V> ConstJson.Result setResult(Class<V> value) {
            return new Result(this.getStatus(), this.getMessage(), new HashMap<String, V>());
        }

        public ConstJson.Result setMessage(String message) {
            this.message = message;
            return this;
        }

        public ConstJson.Result put(Object key, Object value) {
            result.put(key, value);
            return this;
        }

        public Map getResult() {
            return result;
        }
    }
}
