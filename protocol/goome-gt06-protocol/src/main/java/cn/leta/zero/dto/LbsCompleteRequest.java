package cn.leta.zero.dto;

import cn.leta.zero.Gt06Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * LBS扩展信息
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-19.
 * @author Xie Gengcai
 */
public class LbsCompleteRequest extends AbstractRequest {
    private short mcc;
    private byte mnc;
    private short lac;
    /**
     * main cell id
     */
    private short mci;
    /**
     * Main Cell ID Signal Strength.主小区信号强度,值范围是 0x00~0xFF,0x00 信号最弱,0xFF 信号最强。
     */
    private byte mciss;
    /**
     * 邻近小区基站编码,共 6 个,值范围是 0x0000 ~ 0xFFFF。
     */
    private short nci1;
    /**
     * 邻近小区基站信号强度,与 6 个邻近小区基站编码 一一对应。值范围是 0x00~0xFF,这里使用信号强度的绝对值,取值时应该附上负号。
     */
    private byte nciss1;
    private short nci2;
    private byte nciss2;
    private short nci3;
    private byte nciss3;
    private short nci4;
    private byte nciss4;
    private short nci5;
    private byte nciss5;
    private short nci6;
    private byte nciss6;
    @Override
    public Deserializer deserialize(byte[] bytes) throws IOException {
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        // 读取日期时间
        setDateTime(Gt06Util.readDate(byteBuf));
        this.mcc = byteBuf.readShort();
        this.mnc = byteBuf.readByte();
        this.lac = byteBuf.readShort();
        this.mci = byteBuf.readShort();
        this.mciss = byteBuf.readByte();
        this.nci1 = byteBuf.readShort();
        this.nciss1 = (byte) (-1 * byteBuf.readByte());
        this.nci2 = byteBuf.readShort();
        this.nciss2 = (byte) (-1 * byteBuf.readByte());
        this.nci3 = byteBuf.readShort();
        this.nciss3 = (byte) (-1 * byteBuf.readByte());
        this.nci4 = byteBuf.readShort();
        this.nciss4 = (byte) (-1 * byteBuf.readByte());
        this.nci5 = byteBuf.readShort();
        this.nciss5 = (byte) (-1 * byteBuf.readByte());
        this.nci6 = byteBuf.readShort();
        this.nciss6 = (byte) (-1 * byteBuf.readByte());
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

    public short getMci() {
        return mci;
    }

    public void setMci(short mci) {
        this.mci = mci;
    }

    public byte getMciss() {
        return mciss;
    }

    public void setMciss(byte mciss) {
        this.mciss = mciss;
    }

    public short getNci1() {
        return nci1;
    }

    public void setNci1(short nci1) {
        this.nci1 = nci1;
    }

    public byte getNciss1() {
        return nciss1;
    }

    public void setNciss1(byte nciss1) {
        this.nciss1 = nciss1;
    }

    public short getNci2() {
        return nci2;
    }

    public void setNci2(short nci2) {
        this.nci2 = nci2;
    }

    public byte getNciss2() {
        return nciss2;
    }

    public void setNciss2(byte nciss2) {
        this.nciss2 = nciss2;
    }

    public short getNci3() {
        return nci3;
    }

    public void setNci3(short nci3) {
        this.nci3 = nci3;
    }

    public byte getNciss3() {
        return nciss3;
    }

    public void setNciss3(byte nciss3) {
        this.nciss3 = nciss3;
    }

    public short getNci4() {
        return nci4;
    }

    public void setNci4(short nci4) {
        this.nci4 = nci4;
    }

    public byte getNciss4() {
        return nciss4;
    }

    public void setNciss4(byte nciss4) {
        this.nciss4 = nciss4;
    }

    public short getNci5() {
        return nci5;
    }

    public void setNci5(short nci5) {
        this.nci5 = nci5;
    }

    public byte getNciss5() {
        return nciss5;
    }

    public void setNciss5(byte nciss5) {
        this.nciss5 = nciss5;
    }

    public short getNci6() {
        return nci6;
    }

    public void setNci6(short nci6) {
        this.nci6 = nci6;
    }

    public byte getNciss6() {
        return nciss6;
    }

    public void setNciss6(byte nciss6) {
        this.nciss6 = nciss6;
    }
}
