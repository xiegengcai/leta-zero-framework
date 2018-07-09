package cn.leta.zero.server;

import cn.leta.zero.Dispatcher;
import cn.leta.zero.packet.BasePacket;
import com.google.common.base.Throwables;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public abstract class AbstractZeroServerHandler<R extends BasePacket> extends SimpleChannelInboundHandler<R> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Dispatcher dispatcher;

    public AbstractZeroServerHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * 响应完成事件
     * @param ctx
     */
    protected void responseComplete(ChannelHandlerContext ctx,  R packet) {
        logger.info("Respone the request of seqId={}", packet.getSequenceId());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("channel[{}] from {}:{} actived.", ctx.channel().id().asLongText(), remoteAddress.getAddress().getHostAddress(), remoteAddress.getPort());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close(new DefaultChannelPromise(ctx.channel()).addListener((ChannelFutureListener) channelFuture -> {
            logger.error("channel[0x{}] exception, closed. {}", ctx.channel().id().asLongText(), Throwables.getStackTraceAsString(cause));
            ctx.close();
        }));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ctx.disconnect(ctx.newPromise().addListener((ChannelFutureListener) channelFuture -> {
            logger.error("channel[0x{}] handlerRemoved, closed. {}", ctx.channel().id().asLongText());
            ctx.close();
        }));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    protected  void handlerLog(ChannelHandlerContext ctx, R packet) {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("Receive Request from {} of {}, cmd={}, seqId={}"
                , remoteAddress.getAddress().getHostAddress(), ctx.channel().id().asLongText()  , packet.getCmd(), packet.getSequenceId()
        );
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, R packet) throws Exception {
        handlerLog(ctx, packet);
        BasePacket response = dispatcher.dispatch(packet, ctx);
        if (needToResponse(packet) && response != null) {
            ctx.writeAndFlush(response, new DefaultChannelPromise(ctx.channel()).addListener((ChannelFutureListener) channelFuture -> responseComplete(ctx, packet)));
        }
    }

    protected boolean needToResponse(R packet) {
        return true;
    }
}