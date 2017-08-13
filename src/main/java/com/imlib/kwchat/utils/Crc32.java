package com.imlib.kwchat.utils;

/**
 * Created by yjdini on 2017/8/10.
 */
public class Crc32 {
    private long[] CRCTable = new long[256];
    private int crc32 = 0;

    public Crc32() {
        initTable();
    }

    private void initTable(){
        int _loc4_ = 0;
        long _loc2_ = 0;
        long _loc3_ = 0;
        _loc4_ = 0;
        while(_loc4_ < 256)
        {
            _loc2_ = _loc4_;
            _loc3_ = 0;
            while(_loc3_ < 8)
            {
                long i = 3988292384L;
                _loc2_ = (_loc2_ & 1) == 1 ? (_loc2_ >>> 1 ^ i) :_loc2_ >>> 1;
                _loc3_++;
            }
            CRCTable[_loc4_] = _loc2_;
            _loc4_++;
        }
    }

    private void update(String param1, int[] param2) //'Gateway.Login.Req' , [0, p1.length]
    {
        reset();
        int _loc6_ = 0;
        int _loc5_ = param2[0];
        int _loc4_ = param1.length();
        int _loc3_ = ~crc32;
        _loc6_ = _loc5_;
        while(_loc6_ < _loc4_)
        {
            _loc3_ = (int) (CRCTable[(_loc3_ ^ param1.charAt(_loc6_)) & 255] ^ _loc3_ >>> 8);
            _loc6_++;
        }
        crc32 = ~_loc3_;
    }
    private void reset()
    {
        crc32 = 0;
    }
    private int getValue()
    {
        return (int) (crc32 & 4294967295L);
    }

    public int crc32_String(String str) {
        update(str, new int[]{0,str.length()});
        return getValue();
    }

    public static void main(String[] args) {
        Crc32 crc = new Crc32();
        System.out.println(Bytes2hexstr.fromInt(crc.crc32_String("Gateway.RoomJoin.Up")));
    }
}
