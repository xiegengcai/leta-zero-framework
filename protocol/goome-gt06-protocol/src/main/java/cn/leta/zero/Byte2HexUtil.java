package cn.leta.zero;

/**
 * 字节数组与16进制字符串相互转换的工具类
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-14.
 * @author Xie Gengcai
 */
public final class Byte2HexUtil {
    private Byte2HexUtil(){}
    /** 16进制所有的位的字节数组 */
    private final static byte[] HEX = "0123456789ABCDEF".getBytes();
    private final static char LOWWER_A = 'a';
    private final static char UPPER_A = 'A';


    /**
     * 整数到字节数组转换
     *
     * @param n 要转换的整数
     * @return
     */
    public static byte[] int2bytes(int n) {
        byte[] ab = new byte[4];
        ab[0] = (byte) (0xff & n);
        ab[1] = (byte) ((0xff00 & n) >> 8);
        ab[2] = (byte) ((0xff0000 & n) >> 16);
        ab[3] = (byte) ((0xff000000 & n) >> 24);
        return ab;
    }

    /**
     * 短整型到字节数组转换
     * @param n 短整型
     * @return
     */
    public static byte[] short2bytes(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) ((n & 0xFF00) >> 8);
        b[1] = (byte) (n & 0xFF);
        return b;
    }

    /**
     * 字节数组转换成短整型
     *
     * @param b 字节数组
     * @return
     */
    public static short bytes2short(byte[] b) {
        short n = (short) (((b[0] < 0 ? b[0] + 256 : b[0]) << 8) + (b[1] < 0 ? b[1] + 256
                : b[1]));
        return n;
    }

    /**
     * 字节数组到整数的转换
     * @param b 字节数组
     * @return
     */
    public static int bytes2int(byte []b) {
        int s = 0;
        s = ((((b[0] & 0xff) << 8 | (b[1] & 0xff)) << 8) | (b[2] & 0xff)) << 8
                | (b[3] & 0xff);
        return s;
    }

    /**
     * 字节转换到字符
     *
     * @param b 字节
     * @return
     */
    public static char byte2char(byte b) {
        return (char) b;
    }

    /**
     * 16进制char转换成整型
     *
     * @param c
     * @return
     */
    public static int parse(char c) {
        if (c >= LOWWER_A) {
            return (c - LOWWER_A + 10) & 0x0f;
        }
        if (c >= UPPER_A) {
            return (c - UPPER_A + 10) & 0x0f;
        }
        return (c - HEX[0]) & 0x0f;
    }

    /**
     * 字节数组转换成十六进制字符串
     *
     * @param b
     * @return
     */
    public static String bytes2HexString(byte[] b) {
        byte[] buff = new byte[2 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[2 * i] = HEX[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = HEX[b[i] & 0x0f];
        }
        return new String(buff);
    }

    /**
     * 十六进制字符串转换成字节数组
     *
     * @param hexstr
     * @return
     */
    public static byte[] hexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }
}
