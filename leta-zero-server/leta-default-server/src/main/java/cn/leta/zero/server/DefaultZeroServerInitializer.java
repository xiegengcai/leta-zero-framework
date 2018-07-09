package cn.leta.zero.server;

import cn.leta.zero.codec.DefaultZeroServerDecoder;
import cn.leta.zero.codec.DefaultZeroServerEncoder;
import cn.leta.zero.packet.DefaultRequestPacket;
import cn.leta.zero.packet.DefaultResponsePacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-11-30.
 * @author Xie Gengcai
 */
@Component
public class DefaultZeroServerInitializer extends AbstractZeroServerInitializer<DefaultRequestPacket, DefaultResponsePacket> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("logger", new LoggingHandler(LogLevel.INFO))
                // 包长度在第0个字节，偏移0，包长度4一个字节，解完后原包不动，校正-4
                .addFirst(new LengthFieldBasedFrameDecoder(this.serverConf.maxFrameLength(), 0, 4
                        , -4, 0))
                .addLast("decoder", new DefaultZeroServerDecoder(this.codec))
                .addLast("encoder", new DefaultZeroServerEncoder(this.codec))
                .addLast("idel", new IdleStateHandler(serverConf.readerIdle(), this.serverConf.writerIdle(), this.serverConf.bothIdle()))
                .addLast("handler", new DefaultZeroServerHandler(this.dispatcher))
        ;
    }
}
