package com.imlib.kwchat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjdini on 2017/8/8.
 */
public class Decrypt {
    private int salt = 43;
    public int DecryptByte (int b0) {
        int b1 = b0 >>> 3; //255
        int b2 = b0 << 5; //255++
        int b3 = (b1 | b2)%256; //255++ => 255
        b3 = (b3 ^ salt);
        salt = b0;
        return b3;
    }

    List<Integer> str2ints (String str) {
        byte[] a = str.getBytes();
        ArrayList<Integer> b = new ArrayList<Integer>();
        for (int i = 0; i < a.length; ){
//            a[i]-87;
            int a1 = a[i++];
            int a2 = a[i++];
            if(a1>90){
                a1 = a1-87;
            }else{
                a1 = a1 - 48;
            }
            if(a2>90){
                a2 = a2-87;
            }else{
                a2 = a2 - 48;
            }
            b.add(a1*16+a2);
        }
        return b;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader si = new BufferedReader(new InputStreamReader(System.in));
        String str = "19fc3268d3ae35a9cd6eb39d3c50c2e179bc3ae9cf76739bdd3b414b721298052853d2845f8167a14e7991877ef31de895e42cb0cf34322388c5a779c33bb733d10a58c217b894dcc9d2d2b951ce5956f69858861b4466b295fecf75d8e5bc9ebfde";
//        byte[] a = "as1dd".getBytes();//a:[97,115,100,100]
        byte [] k=new byte[1000];
        Decrypt decrypt = new Decrypt();
        List<Integer> ints = decrypt.str2ints(str);
        int j = 0;
        for(Integer i : ints){
            Integer x = decrypt.DecryptByte(new Integer(i).intValue());
            Byte y = x.byteValue();
            k[j++] = y;
        }
        System.out.println(new String(k));
    }
}
