package com.imlib.quanmintv;

/**
 * Created by yjdini on 2017/9/21.
 *
 */
public class BytesUtil {
    public static byte[] intToBytes( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    public static byte[] longToBytes( long value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    public static Integer bytesToInteger( byte[] bytes, int off)
    {
        if (bytes.length < (off + 4)) {
            return 0;
        }
        int b0 = bytes[off] & 0xFF;
        int b1 = bytes[off + 1] & 0xFF;
        int b2 = bytes[off + 2] & 0xFF;
        int b3 = bytes[off + 3] & 0xFF;
        return b0 | (b1 << 8) | (b2 << 16) | (b3 << 24); // 小端表示法
    }

//    public static byte[] stringToBytes( String value )
//    {
//
//    }
}
