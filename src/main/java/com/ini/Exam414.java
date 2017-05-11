package com.ini;

import java.util.HashMap;

/**
 * Created by Somnus`L on 2017/4/14.
 */
public class Exam414 {
    public static void main(String[] args){
        String in = "";
        char[] chars = in.toCharArray();
        HashMap<String, Integer> countMap = new HashMap<String, Integer>();
        for(char c : chars){
            if(countMap.containsKey(c)){
                countMap.put(c+"", countMap.get(c)+1);
            }else{
                countMap.put(c+"", 1);
            }
        }
        Integer const1 = new Integer(1);
        for(int i = 0; i < chars.length; i ++){
            if(const1.equals(countMap.get(chars[i]))){
                System.out.println(chars[i]);
                break;
            }
        }
    }

}
