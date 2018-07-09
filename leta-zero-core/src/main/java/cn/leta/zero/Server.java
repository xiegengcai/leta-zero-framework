package cn.leta.zero;

import java.net.InetSocketAddress;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/25.
 * @author Xie Gengcai
 */
public interface Server extends Endpoint {
    /**
     * 获取本地地址
     * @return
     */
    InetSocketAddress localAddress();
}