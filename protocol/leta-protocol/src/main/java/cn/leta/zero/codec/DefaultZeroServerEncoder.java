package cn.leta.zero.codec;


import cn.leta.zero.packet.DefaultResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public class DefaultZeroServerEncoder extends AbstractZeroServerEncoder<DefaultResponsePacket> {
    public DefaultZeroServerEncoder(Codec codec) {
        super(codec);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, DefaultResponsePacket packet, ByteBuf out) throws Exception {
        LOGGER.debug("原始包长度:{}", packet.getLength());
        // 加密
        byte []  body = codec.encrypt(ctx.channel(), packet.getBody());
        packet.setBody(body);
        LOGGER.debug("加密后包长度:{}", packet.getLength());
        // 写入包长度
        out.writeInt(packet.getLength());
        // 写入序列ID
        out.writeLong(packet.getSequenceId());
        // 写入指令码
        out.writeShort(packet.getCmd());
        // 写入响应码
        out.writeInt(packet.getCode());
        // 写入序列化方式
        out.writeByte(packet.getSerialization());
        // 写入body
        if (body != null) {
            out.writeBytes(body);
        }
    }
}