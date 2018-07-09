package cn.leta.zero.server;

import cn.leta.zero.codec.Gt06ServerDecoder;
import cn.leta.zero.codec.Gt06ServerEncoderAbstract;
import cn.leta.zero.packet.Gt06Packet;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-05.
 * @author Xie Gengcai
 */
@Component
public class Gt06ServerInitializer extends AbstractZeroServerInitializer<Gt06Packet, Gt06Packet> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("logger", new LoggingHandler(LogLevel.INFO))
                // 固定分隔符解包，解包后包含分隔符
                .addLast(new DelimiterBasedFrameDecoder(this.serverConf.maxFrameLength(),  false, Unpooled.copyInt(Gt06Packet.MAGIC_STOP_NUMBER)))
                .addLast("decoder", new Gt06ServerDecoder(this.codec))
                .addLast("encoder", new Gt06ServerEncoderAbstract(this.codec))
                .addLast("idel", new IdleStateHandler(serverConf.readerIdle(), this.serverConf.writerIdle(), this.serverConf.bothIdle()))
                .addLast("handler", new Gt06ServerHandler(this.dispatcher))
        ;
    }
}
