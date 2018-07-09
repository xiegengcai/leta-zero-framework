package cn.leta.zero.server;

import cn.leta.zero.Crc16Util;
import cn.leta.zero.Dispatcher;
import cn.leta.zero.packet.Gt06Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import static cn.leta.zero.Gt06Constants.Command;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-05.
 * @author Xie Gengcai
 */
@ChannelHandler.Sharable
public class Gt06ServerHandler extends AbstractZeroServerHandler<Gt06Packet> {
    public Gt06ServerHandler(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Gt06Packet packet) throws Exception {
        int crc = Crc16Util.doCrc(packet.getCrcData());
        if (packet.getCrc() != crc) {
            logger.error("校验码不一致[{}, {},]", Integer.toHexString(packet.getCrc()), Integer.toHexString(crc));
            return;
        }
        super.channelRead0(ctx, packet);
    }

    @Override
    protected boolean needToResponse(Gt06Packet packet) {
        short cmd = packet.getCmd();
        if (cmd== Command.LOGIN
                || cmd == Command.GPS_PHONE_SEARCH_MSG
                || cmd == Command.STATE_MSG
                || cmd == Command.GPS_LBS_STATE_MSG) {
            return true;
        }
        return false;
    }
}
