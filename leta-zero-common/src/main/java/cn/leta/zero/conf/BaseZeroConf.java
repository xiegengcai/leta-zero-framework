package cn.leta.zero.conf;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/25.
 * @author Xie Gengcai
 */
public abstract class BaseZeroConf {


    @Value("${rpc.threadSize.boss:1}")
    private int bossThreadSize;
    @Value("${rpc.packet.E:8192}")
    private int maxFrameLength;
    @Value("${rpc.channel.read.idle}")
    private int readerIdle;
    @Value("${rpc.channel.write.idle}")
    private int writerIdle;
    @Value("${rpc.channel.both.idle}")
    private int bothIdle;
    @Value("${rpc.maxTimeoutInterval:120}")
    private int maxTimeoutInterval;
    @Value("${rpc.supported.serialization}")
    private String supportedSerialization;


    public int bossThreadSize() {
        return this.bossThreadSize;
    }

    /**
     * 读空闲时间
     * @return
     */
    public int readerIdle() {
        return this.readerIdle;
    }

    /**
     * 写空闲时间
     * @return
     */
    public int writerIdle() {
        return this.writerIdle;
    }

    /**
     * 读写空闲时间
     * @return
     */
    public int bothIdle() {
        return this.bothIdle;
    }
    public int maxFrameLength() {
        return this.maxFrameLength;
    }
    public int maxTimeoutInterval(){return this.maxTimeoutInterval;}

    public String supportedSerialization() {
        return this.supportedSerialization;
    }
}
