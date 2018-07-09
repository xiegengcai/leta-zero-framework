package cn.leta.zero.server;

import cn.leta.zero.Dispatcher;
import cn.leta.zero.codec.Codec;
import cn.leta.zero.conf.ZeroServerConf;
import cn.leta.zero.packet.BasePacket;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-11-30.
 * @author Xie Gengcai
 */
public abstract class AbstractZeroServerInitializer<M extends BasePacket, R extends BasePacket> extends ChannelInitializer<SocketChannel> {

    @Autowired
    protected Codec codec;
    @Autowired
    protected ZeroServerConf serverConf;
    @Autowired
    protected Dispatcher<M, R> dispatcher;

}
