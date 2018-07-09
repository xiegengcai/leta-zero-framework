package cn.leta.zero.server;

import cn.leta.zero.Dispatcher;
import cn.leta.zero.packet.DefaultRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-11-30.
 * @author Xie Gengcai
 */
@ChannelHandler.Sharable
public class DefaultZeroServerHandler extends AbstractZeroServerHandler<DefaultRequestPacket> {
    public DefaultZeroServerHandler(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    protected void handlerLog(ChannelHandlerContext ctx, DefaultRequestPacket packet) {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("Receive Request from {} of {}, cmd={}, version={}, seqId={}"
                , remoteAddress.getAddress().getHostAddress(), ctx.channel().id().asLongText()
                , packet.getCmd(), packet.getVersion(), packet.getSequenceId()
        );
    }
}
