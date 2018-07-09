package cn.leta.zero.packet;

/**
 * <pre>数据包定义</pre>
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public class DefaultPacket extends BasePacket {

    protected final static int MIN_LENGTH = 15;

    @Override
    public int getHeaderLength() {
        return MIN_LENGTH;
    }

    /**
     * 序列化协议。1字节
     */
    private byte serialization;

    public byte getSerialization() {
        return serialization;
    }

    public void setSerialization(byte serialization) {
        this.serialization = serialization;
    }

}
