package cn.leta.zero.codec;

import cn.leta.zero.Byte2HexUtil;
import cn.leta.zero.packet.Gt06Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-01.
 * @author Xie Gengcai
 */
public class Gt06ServerDecoder extends AbstractZeroServerDecoder {

    public Gt06ServerDecoder(Codec codec) {
        super(codec);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            byte[] pack = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(0, pack);
            LOGGER.debug(Byte2HexUtil.bytes2HexString(pack));
        }
        Gt06Packet packet = new Gt06Packet();
        // 校验数据。协议体中从“包长度”到“信息序列号”(包括“包长度”、“信息序列号”)。即N - 6(起始位2、错误校验码2、结束位2)， From 2
        byte[] crcData = new byte[byteBuf.readableBytes() - 6];
        // getBytes 不改变readerIndex
        byteBuf.getBytes(2, crcData);
        packet.setCrcData(crcData);

        // 读取磨数。2byte
        packet.setMagicNumber(byteBuf.readShort());
        // 读取包长度。1byte。无符号byte转为有符号值
        int packetLen = byteBuf.readUnsignedByte();
        LOGGER.debug("原始包长度：{}", packetLen);
        packet.setLength(packetLen);
        // 读取协议号。1byte
        packet.setCmd(byteBuf.readByte());
        // body 长度 = 包长 - 10 + 5 (长度=协议号+信息内容+信息序列号+错误校验)
        byte [] tytes = new byte[packetLen - packet.getHeaderLength() + 5];
        byteBuf.readBytes(tytes);
        packet.setBody(tytes);
        // 解密
        packet.setBody(codec.decrypt(ctx.channel(), tytes));
        // 解密后长度
        packetLen = packet.getLength();
        LOGGER.debug("解密后包长度：{}", packetLen);
        // 序列号。2byte
        packet.setSequenceId(byteBuf.readShort());
        // CRC。2byte
        packet.setCrc(byteBuf.readShort());
        // 2byte
        packet.setMagicStop(byteBuf.readShort());
        list.add(packet);
    }
}
