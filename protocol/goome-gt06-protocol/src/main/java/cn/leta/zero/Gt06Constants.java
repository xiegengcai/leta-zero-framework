package cn.leta.zero;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-06.
 * @author Xie Gengcai
 */
public interface Gt06Constants {
    /**
     * 协议定义一个字节，无符号0~255，java都有符号
     * 指令码（协议号）
     */
    interface Command {
        /**
         * 登录
         */
        short LOGIN                 = 0x01;
        /**
         * GPS位置上报
         */
        short GPS_MSG               = 0x10;
        /**
         * LBS位置上报
         */
        short LBS_MSG               =  0x11;
        /**
         * GPS、LBS合并信息
         */
        short GPS_LBS_MSG           = 0x12;
        /**
         * 状态信息
         */
        short STATE_MSG             = 0x13;
        /**
         * 卫星信噪比
         */
        short SATELLITE_SNR         = 0x14;
        /**
         * 字符串信息
         */
        short STRING_MSG            = 0x15;
        /**
         * GPS、LBS、状态合并信息
         */
        short GPS_LBS_STATE_MSG     = 0x16;
        /**
         * LBS、电话号码查询地址信息
         */
        short LBS_PHONE_SEARCH_MSG  = 0x17;
        /**
         * lbs完整信息
         */
        short LBS_COMPLETE_MSG      = 0x18;
        /**
         * GPS、电话号码查询地址信息
         */
        short GPS_PHONE_SEARCH_MSG  = 0x1A;
        /**
         * 服务器向终端推送消息
         */
        short SERVER_PUSH_MSG       = 0x80;
    }
}
