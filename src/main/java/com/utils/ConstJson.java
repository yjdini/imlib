package com.utils;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public class ConstJson {
    public final static Status OK = new Status("ok");
    public final static Status UNLOGIN = new Status("unlogin");
    public final static Status ERROR = new Status("error");

    public static Status getStatusByResult(boolean b) {
    }

    public static class Status{
        private String status;
        public Status(String status){
            this.status = status;
        }
    }
}
