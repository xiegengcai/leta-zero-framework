package cn.leta.zero;

import cn.leta.zero.exception.ZeroException;
import cn.leta.zero.packet.BasePacket;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务派发器
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/26.
 * @author Xie Gengcai
 */
public interface Dispatcher<R extends BasePacket, P extends BasePacket> {

    /**
     * 分发请求
     * @param request 请求包
     * @param ctx Context。目的是为了下层业务代码取到
     * @return
     * @throws ZeroException
     */
    P dispatch(R request, ChannelHandlerContext ctx) throws ZeroException;
}