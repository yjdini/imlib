package com.imlib.kwchat.utils;

/**
 * Created by yjdini on 2017/8/11.
 */
public class Bytes2hexstr {
    public static String fromInt(int in){
        long x = in > 0 ? in : 0xFFFFFFFFL + 1 + in;
        int[] hs = new int[8];
        for (int i = 0; i < 8; i ++){
            hs[i] = (int) (x%16);
            x = (x - hs[i])/16;
        }
        StringBuilder str = new StringBuilder();
        for(int i = 7 ; i > -1; i --){
            str.append(int2hex(hs[i]));
        }
        return str.toString();
    }

    private static String int2hex(int i){
        switch(i){
            case 10: return "a";
            case 11: return "b";
            case 12: return "c";
            case 13: return "d";
            case 14: return "e";
            case 15: return "f";
        }
        return i+"";
    }


}
