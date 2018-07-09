package cn.leta.zero.codec;


import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public abstract class AbstractZeroServerDecoder extends ByteToMessageDecoder {
    protected final static Logger LOGGER = LoggerFactory.getLogger(AbstractZeroServerDecoder.class);
    protected Codec codec;

    public AbstractZeroServerDecoder(Codec codec) {
        this.codec = codec;
    }

}