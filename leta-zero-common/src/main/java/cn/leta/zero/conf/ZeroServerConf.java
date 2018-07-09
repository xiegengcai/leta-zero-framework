package cn.leta.zero.conf;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public class ZeroServerConf extends BaseZeroConf {
    @Value("${rpc.port}")
    private int port;
    @Value("${rpc.threadSize.woker:3}")
    private int wokerThreadSize;
    @Value("${rpc.service.maxTimeout:60}")
    private int maxTimeout;
    @Value("${rpc.service.defaultTimeout:30}")
    private int defaultTimeout;

    public int port() {
        return this.port;
    }
    public int wokerThreadSize() {
        return this.wokerThreadSize;
    }

    /**
     * 最大超时时间60s,单位秒
     */
    public int maxTimeout() {
        return this.maxTimeout();
    }

    /**
     * 缺省超时时间 30s,单位秒
     */
    public int defaultTimeout() {
        return this.defaultTimeout;
    }
}
