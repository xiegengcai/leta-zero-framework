package cn.leta.zero.server;

import cn.leta.zero.Constants;
import cn.leta.zero.Dispatcher;
import cn.leta.zero.ZeroContext;
import cn.leta.zero.filter.ZeroFilterValue;
import cn.leta.zero.packet.BasePacket;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/26.
 * @author Xie Gengcai
 */
public abstract class AbstractZeroServerDispatch<M extends BasePacket, R extends BasePacket> implements Dispatcher<M, R>  {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ZeroContext zeroContext;

    protected int doBefore(M request, ChannelHandlerContext ctx) {
        for(ZeroFilterValue filterValue : zeroContext.getFilters()) {
            if (!filterValue.getFilter().isMatch(request)){
                continue;
            }
            int errorCode = filterValue.getFilter().before(request, ctx);
            if (errorCode != Constants.ErrorCode.SUCCESS.getCode()) {
                return errorCode;
            }
        }
        return Constants.ErrorCode.SUCCESS.getCode();
    }

    protected int doAfter(M request, Object message, ChannelHandlerContext ctx) {
        for(ZeroFilterValue filterValue : zeroContext.getFilters().descendingSet()) {
            if (!filterValue.getFilter().isMatch(request)){
                continue;
            }
            int errorCode = filterValue.getFilter().after(message, ctx);
            if (errorCode != Constants.ErrorCode.SUCCESS.getCode()) {
                return errorCode;
            }
        }
        return Constants.ErrorCode.SUCCESS.getCode();
    }
}
