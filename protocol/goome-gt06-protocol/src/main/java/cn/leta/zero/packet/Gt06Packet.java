package cn.leta.zero.packet;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-04.
 * @author Xie Gengcai
 */
public class Gt06Packet extends BasePacket {
    /**
     * 最小包长
     */
    protected final static int MIN_LENGTH = 10;
    public final static short MAGIC_NUMBER = 0x7878;
    public final static short MAGIC_STOP_NUMBER = 0x0D0A;
    /**
     * 协议魔数。2字节
     */
    private short magicNumber;

    /**
     * CRC校验码， 2 byte
     */
    private short crc;

    /**
     * 停止位。2byte，没什么用，可以直接通过包长判断是否半包/粘包
     */
    private short magicStop;

    /**
     * 校验数据。协议体中从“包长度”到“信息序列号”(包括“包长度”、“信息序列号”)
     */
    private byte [] crcData;

    @Override

    public int getHeaderLength() {
        return MIN_LENGTH;
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public short getCrc() {
        return crc;
    }

    public void setCrc(short crc) {
        this.crc = crc;
    }

    public short getMagicStop() {
        return magicStop;
    }

    public void setMagicStop(short magicStop) {
        this.magicStop = magicStop;
    }

    public byte[] getCrcData() {
        return crcData;
    }

    public void setCrcData(byte[] crcData) {
        this.crcData = crcData;
    }
}
