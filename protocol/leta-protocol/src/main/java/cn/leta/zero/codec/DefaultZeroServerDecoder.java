package cn.leta.zero.codec;


import cn.leta.zero.packet.DefaultRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public class DefaultZeroServerDecoder extends AbstractZeroServerDecoder {

    public DefaultZeroServerDecoder(Codec codec) {
        super(codec);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        DefaultRequestPacket packet = new DefaultRequestPacket();
        int packetLen = byteBuf.readInt();
        LOGGER.debug("原始包长度：{}", packetLen);
        // 设置序列ID
        packet.setSequenceId(byteBuf.readInt());
        // 设置指令代码
        packet.setCmd(byteBuf.readShort());
        // 设置指令版本
        packet.setVersion(byteBuf.readByte());
        // 设置序列化方式
        packet.setSerialization(byteBuf.readByte());
        byte [] tytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(tytes);
        // 解密
        packet.setBody(codec.decrypt(ctx.channel(), tytes));
        // 解密后长度
        packetLen = packet.getLength();
        LOGGER.debug("解密后包长度：{}", packetLen);
        list.add(packet);
    }

}