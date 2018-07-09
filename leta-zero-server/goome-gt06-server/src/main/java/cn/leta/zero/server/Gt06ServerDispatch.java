package cn.leta.zero.server;

import cn.leta.zero.Constants;
import cn.leta.zero.SpiMethodHandler;
import cn.leta.zero.dto.Deserializer;
import cn.leta.zero.dto.Serializer;
import cn.leta.zero.exception.ZeroBizException;
import cn.leta.zero.exception.ZeroException;
import cn.leta.zero.packet.Gt06Packet;
import com.google.common.base.Throwables;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-05.
 * @author Xie Gengcai
 */
@Component
public class Gt06ServerDispatch extends AbstractZeroServerDispatch<Gt06Packet, Gt06Packet> {
    private final int DEFAULT_VERSION = 1;
    @Override
    public Gt06Packet dispatch(Gt06Packet request, ChannelHandlerContext ctx) throws ZeroException {
        int errorCode = doBefore(request, ctx);
        if (errorCode != Constants.ErrorCode.SUCCESS.getCode()) {
            logger.info("指令{}#{}前置拦截器返回错误：code={}", request.getCmd(), errorCode);
            request.setBody(null);
            return request;
        }
        // 获取处理器
        SpiMethodHandler spiMethodHandler = zeroContext.getSpiMethodHandler(request.getCmd(), DEFAULT_VERSION);
        Gt06Packet response = new Gt06Packet();
        response.setMagicNumber(Gt06Packet.MAGIC_NUMBER);
        response.setCmd(request.getCmd());
        response.setSequenceId(request.getSequenceId());
        response.setMagicStop(Gt06Packet.MAGIC_STOP_NUMBER);

        if (spiMethodHandler == null) {
            logger.error("指令{}#{}不存在", request.getCmd(), DEFAULT_VERSION);
            return response;
        }
        if (spiMethodHandler.getSpiMethodValue().isObsoleted()) {
            logger.error("指令{}#{}已过期", request.getCmd(), DEFAULT_VERSION);
            return response;
        }
        return doInvoke(request, ctx, spiMethodHandler, response);
    }

    private Gt06Packet doInvoke(Gt06Packet request, ChannelHandlerContext ctx, SpiMethodHandler spiMethodHandler, Gt06Packet response) {
        int errorCode = doBefore(request, ctx);
        if (errorCode != Constants.ErrorCode.SUCCESS.getCode()) {
            logger.info("指令{}#{}前置拦截器返回错误：code={}", request.getCmd(), DEFAULT_VERSION, errorCode);
            return response;
        }
        try {
            Class<?> [] paramterTypes =spiMethodHandler.getHandlerMethod().getParameterTypes();
            List<Object> params = new ArrayList() ;
            if (paramterTypes.length > 0) {
                for (Class<?> paramterType : paramterTypes) {
                    if (ClassUtils.isAssignable(Deserializer.class, paramterType)) {
                        params.add(((Deserializer) Class.forName(paramterType.getName()).newInstance()).deserialize(request.getBody()));
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
                logger.info("指令{}#{}后置置拦截器返回错误：code={}", request.getCmd(), DEFAULT_VERSION, errorCode);
                return response;
            }
            if (result !=null) {
                Serializer serializer = (Serializer)result;
                // 序列化
                response.setBody(serializer.serialize());
            }
        } catch (ZeroBizException e) {
            logger.error("指令{}#{}业务异常，code={}，message={}", request.getCmd(), DEFAULT_VERSION, e.getCode(), e.getMessage());
        }catch (Exception e) {
            if (e.getCause() instanceof ZeroBizException) {
                ZeroBizException rpcException = (ZeroBizException) e.getCause();
                logger.error("指令{}#{}业务异常，code={}，message={}", request.getCmd(), DEFAULT_VERSION,  rpcException.getCode(), rpcException.getMessage());
            } else {
                logger.error("指令{}#{}未知错误, {}", request.getCmd(), DEFAULT_VERSION, Throwables.getStackTraceAsString(e));
            }
        }
        return response;
    }
}
