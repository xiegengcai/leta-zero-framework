package cn.leta.zero.dto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-21.
 * @author Xie Gengcai
 */
public class LbsPhoneRquest implements Deserializer{

    private short mcc;
    private byte mnc;
    private short lac;
    private int cellId;
    private String phoneNumber;

    @Override
    public Deserializer deserialize(byte[] bytes) throws IOException {
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        this.mcc = byteBuf.readShort();
        this.mnc = byteBuf.readByte();
        this.lac = byteBuf.readShort();
        // cell 3bytes 0x000000 ~ 0xFFFFFF
        int first = byteBuf.readByte() & 0xFF0000;
        this.cellId = first + byteBuf.readShort();
        byte[] phoneNum = new byte[21];
        byteBuf.readBytes(phoneNum);
        this.phoneNumber = new String(phoneNum);
        return this;
    }

    public short getMcc() {
        return mcc;
    }

    public void setMcc(short mcc) {
        this.mcc = mcc;
    }

    public byte getMnc() {
        return mnc;
    }

    public void setMnc(byte mnc) {
        this.mnc = mnc;
    }

    public short getLac() {
        return lac;
    }

    public void setLac(short lac) {
        this.lac = lac;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
