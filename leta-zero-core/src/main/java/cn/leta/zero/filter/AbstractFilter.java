package cn.leta.zero.filter;

import cn.leta.zero.annotation.ZeroFilter;
import cn.leta.zero.packet.BasePacket;
import io.netty.channel.ChannelHandlerContext;

/**
 * <pre>
 *     过滤器接口
 * </pre>
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/9/2.
 * @author Xie Gengcai
 */
public abstract class AbstractFilter<R extends BasePacket> {
    /**
     * 是否拦截该指令
     * @param req
     * @return
     */
    public boolean isMatch(R req) {
        ZeroFilter zeroFilter = this.getClass().getAnnotation(ZeroFilter.class);
        for (int cmd : zeroFilter.ignoreCmds()) {
            if (cmd == req.getCmd()) {
                return false;
            }
        }
        for (int cmd : zeroFilter.cmds()) {
            if (cmd == req.getCmd()) {
                return true;
            }
        }
        return true;
    }
    /**
     * 前置拦截
     *
     * @param req
     * @param ctx
     * @return
     */
    public abstract int before(R req, ChannelHandlerContext ctx);

    /**
     * 后置拦截
     *
     * @param message
     * @param cxt
     * @return
     */
    public abstract int after(Object message, ChannelHandlerContext cxt);
}
