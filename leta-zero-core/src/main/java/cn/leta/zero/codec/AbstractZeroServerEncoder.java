package cn.leta.zero.codec;


import cn.leta.zero.packet.BasePacket;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public abstract class AbstractZeroServerEncoder<R extends BasePacket>  extends MessageToByteEncoder <R>{
    protected final static Logger LOGGER = LoggerFactory.getLogger(AbstractZeroServerDecoder.class);
    protected Codec codec;
    public AbstractZeroServerEncoder(Codec codec) {
        this.codec = codec;
    }
}