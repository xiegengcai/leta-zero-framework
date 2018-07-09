package cn.leta.zero;

import io.netty.buffer.ByteBuf;

import java.time.LocalDateTime;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-15.
 * @author Xie Gengcai
 */
public final class Gt06Util {
    public static LocalDateTime readDate(ByteBuf byteBuf) {
        return LocalDateTime.of(2000+byteBuf.readByte(), byteBuf.readByte(), byteBuf.readByte(), byteBuf.readByte(),
                byteBuf.readByte(), byteBuf.readByte());
    }
    public static byte[] imeiString2Bytes(String src) {
        StringBuilder imei = new StringBuilder(src);
        if (imei.length() % 2 > 0) {
            imei.insert(0, 0);
        }
        int length = imei.length() / 2;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i ++) {
            int start = i * 2;
            bytes[i] = Byte.valueOf(imei.substring(start, start +2));
        }
        return bytes;
    }

    public static String bytes2ImeiStr(byte [] bytes) {
        StringBuilder imei = new StringBuilder();
        int index = 0;
        for (byte b : bytes) {
            if (b > 10 || index == 0) {
                imei.append(b);
            } else {
                imei.append(0).append(b);
            }
            index ++;
        }
        return imei.toString();
    }

    public static String bytes2HexStr(byte [] bytes) {
        StringBuilder hexStr = new StringBuilder();
        for(int i =0; i < bytes.length; ++i) {
            hexStr.append(String.format("%02x", bytes[i]));
        }
        return hexStr.toString();
    }

    /**
     * 一个分拆高低位
     * @param data
     * @return
     */
    public static byte[] splitTwoByte(byte data) {
        byte[] b = new byte[2];
        b[0] = (byte) ((data & 0xf0) >> 4);
        b[1] = (byte) (data & 0x0f);
        return b;
    }
}
