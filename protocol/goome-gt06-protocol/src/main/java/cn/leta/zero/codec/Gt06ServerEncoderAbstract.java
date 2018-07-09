package cn.leta.zero.codec;

import cn.leta.zero.Crc16Util;
import cn.leta.zero.packet.Gt06Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-04.
 * @author Xie Gengcai
 */
public class Gt06ServerEncoderAbstract extends AbstractZeroServerEncoder<Gt06Packet> {
    public Gt06ServerEncoderAbstract(Codec codec) {
        super(codec);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Gt06Packet packet, ByteBuf byteBuf) throws Exception {
        // 设置body时会算真实包长度
        LOGGER.debug("原始包长度:{}", packet.getLength());
        // 加密
        byte []  body = codec.encrypt(ctx.channel(), packet.getBody());
        // 设置body时会算真实包长度
        packet.setBody(body);
        // 写入起始位 magic。 2byte
        byteBuf.writeShort(packet.getMagicNumber());
        // 写入包长度(协议中包长度=协议号+信息内容+信息序列号+错误校验，即5+N)。1byte
        int protocolLength = packet.getBody().length + 5;
        byteBuf.writeByte(protocolLength);
        // 写入协议号。。1byte
        byteBuf.writeByte(packet.getCmd());
        // 写入body。
        if (body != null) {
            byteBuf.writeBytes(body);
        }
        // 写入序列号。2byte
        byteBuf.writeShort((int) packet.getSequenceId());
        // 校验数据。协议体中从“包长度”到“信息序列号”(包括“包长度”、“信息序列号”)。即去除起始位-2
        byte[] crcData = new byte[byteBuf.readableBytes() - 2];
        byteBuf.getBytes(2, crcData);
        // 写入 CRC。2byte
        byteBuf.writeShort(Crc16Util.doCrc(crcData));
        // 写入结束位。2byte
        byteBuf.writeShort(packet.getMagicStop());
    }
}
