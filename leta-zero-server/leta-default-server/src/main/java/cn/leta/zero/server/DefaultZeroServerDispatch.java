package cn.leta.zero.server;

import cn.leta.zero.Constants;
import cn.leta.zero.SpiMethodHandler;
import cn.leta.zero.ZeroContext;
import cn.leta.zero.exception.ZeroBizException;
import cn.leta.zero.exception.ZeroException;
import cn.leta.zero.packet.DefaultRequestPacket;
import cn.leta.zero.packet.DefaultResponsePacket;
import cn.leta.zero.serialize.Serialization;
import com.google.common.base.Throwables;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-11-30.
 * @author Xie Gengcai
 */
@Component
public class DefaultZeroServerDispatch extends AbstractZeroServerDispatch<DefaultRequestPacket, DefaultResponsePacket> {


    @Override
    public DefaultResponsePacket dispatch(DefaultRequestPacket request, ChannelHandlerContext ctx) throws ZeroException {
        // 获取处理器
        SpiMethodHandler spiMethodHandler = zeroContext.getSpiMethodHandler(request.getCmd(), request.getVersion());
        DefaultResponsePacket response = new DefaultResponsePacket();
        response.setSequenceId(request.getSequenceId());
        response.setSerialization(request.getSerialization());
        response.setCmd(request.getCmd());
        if (spiMethodHandler == null) {
            logger.error("指令{}#{}不存在", request.getCmd(), request.getVersion());
            response.setCode(Constants.ErrorCode.SERVICE_ERROR.getCode());
            return response;
        }
        if (spiMethodHandler.getSpiMethodValue().isObsoleted()) {
            logger.error("指令{}#{}已过期", request.getCmd(), request.getVersion());
            response.setCode(Constants.ErrorCode.OBSOLETED_METHOD.getCode());
            return response;
        }
        return doInvoke(request, ctx, spiMethodHandler, response);
    }

    private DefaultResponsePacket doInvoke(DefaultRequestPacket request, ChannelHandlerContext ctx, SpiMethodHandler spiMethodHandler, DefaultResponsePacket response) {
        int errorCode = doBefore(request, ctx);
        if (errorCode != Constants.ErrorCode.SUCCESS.getCode()) {
            response.setCode(errorCode);
            logger.info("指令{}#{}前置拦截器返回错误：code={}", request.getCmd(), request.getVersion(), errorCode);
            return response;
        }
        try {
            Class<?> [] paramterTypes =spiMethodHandler.getHandlerMethod().getParameterTypes();
            List<Object> params = new ArrayList() ;
            Serialization serialization = ZeroContext.getSerialization(request.getSerialization());
            if (paramterTypes.length > 0) {
                for (Class<?> paramterType : paramterTypes) {
                    if (ClassUtils.isAssignable(Object.class, paramterType)) {
                        // 反序列化
                        params.add(serialization.deserialize(request.getBody(), paramterType));
                    } else if (ClassUtils.isAssignable(ChannelHandlerContext.class, paramterType)) {
                        params.add(ctx);
                    } else if (ClassUtils.isAssignable(long.class, paramterType)) {
                        params.add(request.getSequenceId());
                    }
                }
            }
            Object result = spiMethodHandler.getHandlerMethod().invoke(spiMethodHandler.getHandler(), params.toArray());
            errorCode = doAfter(request, result, ctx);
            if (errorCode != Constants.ErrorCode.SUCCESS.getCode()) {
                response.setCode(errorCode);
                logger.info("指令{}#{}后置置拦截器返回错误：code={}", request.getCmd(), request.getVersion(), errorCode);
                return response;
            }
            response.setCode(Constants.ErrorCode.SUCCESS.getCode());
            // 序列化
            response.setBody(ZeroContext.getSerialization(request.getSerialization()).serialize(result));
        } catch (ZeroBizException e) {
            logger.error("指令{}#{}业务异常，code={}，message={}", request.getCmd(), request.getVersion(), e.getCode(), e.getMessage());
            response.setCode(e.getCode());
        }catch (Exception e) {
            if (e.getCause() instanceof ZeroBizException) {
                ZeroBizException rpcException = (ZeroBizException) e.getCause();
                logger.error("指令{}#{}业务异常，code={}，message={}", request.getCmd(), request.getVersion(), rpcException.getCode(), rpcException.getMessage());
                response.setCode(rpcException.getCode());
            } else {
                logger.error("指令{}#{}未知错误, {}", request.getCmd(), request.getVersion(), Throwables.getStackTraceAsString(e));
                response.setCode(Constants.ErrorCode.UNKNOW_ERROR.getCode());
            }
        }
        return response;
    }
}
