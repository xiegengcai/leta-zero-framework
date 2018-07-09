package cn.leta.zero.packet;

/**
 * <pre>请求数据包</pre>
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/18.
 * @author Xie Gengcai
 */
public class DefaultRequestPacket extends DefaultPacket {
    protected final static int HEADER_LENGTH = MIN_LENGTH + 1;
    /**
     * 指令版本号，1字节
     */
    private byte version;


    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    @Override
    public int getHeaderLength() {
        return HEADER_LENGTH;
    }

}