package com.utils;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public class ConstJson {
    public final static Result OK = new Result("ok");
    public final static Result UNLOGIN = new Result("unlogin");
    public final static Result ERROR = new Result("error");
    public final static Result UNREGIST = new Result("unRegist");
    public final static Result PSDERR = new Result("psdErr");

    public static class Result{
        private String status;
        public Result(String status){
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
