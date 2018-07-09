package cn.leta.zero.packet;

/**
 * <pre>数据包定义</pre>
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/24.
 * @author Xie Gengcai
 */
public abstract class BasePacket {

    /**
     * 包长度。4字节
     */
    private int length;

    /**
     * 指令代码。2字节
     */
    private short cmd;

    /**
     * 客户端唯一的序列ID， 8字节
     */
    private long sequenceId;

    private byte[] body;

    /**
     * 包头长度
     * @return
     */
    public abstract int getHeaderLength();


    public int getLength() {
        if (length == 0) {
            return getHeaderLength();
        }
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public byte[] getBody() {
        return body;
    }

    public void setBody(byte [] body) {
        this.body = body;
        if (this.body == null) {
            this.length = getHeaderLength();
        } else {
            this.length = getHeaderLength() + this.body.length;
        }
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }
}
