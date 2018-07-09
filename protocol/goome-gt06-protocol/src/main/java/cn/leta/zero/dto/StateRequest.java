package cn.leta.zero.dto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-21.
 * @author Xie Gengcai
 */
public class StateRequest implements Deserializer {
    /**
     * 是否设防
     */
    private boolean defence;
    /**
     * ACC 高？
     */
    private boolean highAcc;
    /**
     * 充电中
     */
    private boolean charging;
    /**
     * 告警信息。000正常，001震动报警，010断电报警，011低电报警，100SOS求救
     */
    private byte warn;
    /**
     * GPS是否已定位
     */
    private boolean located;
    /**
     * 断油电
     */
    private boolean powerOff;
    /** 电压等级。范围为 0~6,标示电压大小由低到高，
     *   0:低电关机;
     *   1:电量不足以打电话发短信等;
     *   2:低电报警;
     *   3:低电,可正常使用;
     *   3~6:均可正常使用,只是依据电量多少不同而排列。
     */
    private byte voltageLevel;
    /** GSM 信号强度等级
     *  0x00:无信号;
     *  0x01:信号极弱
     *  0x02:信号较弱
     *  0x03:信号良好
     *  0x04:信号强
     */
    private byte signalLevel;
    @Override
    public Deserializer deserialize(byte[] bytes) throws IOException {
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        short terminalMsg = byteBuf.readUnsignedByte();
        this.defence = (terminalMsg & 0B0000_0001) > 0;
        this.highAcc = (terminalMsg & 0B0000_0010) > 0;
        this.charging = (terminalMsg & 0B0000_0100) > 0;
        this.warn = (byte) ((terminalMsg & 0B0011_1000) >> 3);
        this.located = (terminalMsg & 0B0100_0000) > 0;
        this.powerOff = (terminalMsg & 0B1000_0000) > 0;
        this.voltageLevel = byteBuf.readByte();
        this.signalLevel = byteBuf.readByte();
        return this;
    }

    public boolean isDefence() {
        return defence;
    }

    public void setDefence(boolean defence) {
        this.defence = defence;
    }

    public boolean isHighAcc() {
        return highAcc;
    }

    public void setHighAcc(boolean highAcc) {
        this.highAcc = highAcc;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public byte getWarn() {
        return warn;
    }

    public void setWarn(byte warn) {
        this.warn = warn;
    }

    public boolean isLocated() {
        return located;
    }

    public void setLocated(boolean located) {
        this.located = located;
    }

    public boolean isPowerOff() {
        return powerOff;
    }

    public void setPowerOff(boolean powerOff) {
        this.powerOff = powerOff;
    }

    public byte getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(byte voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public byte getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(byte signalLevel) {
        this.signalLevel = signalLevel;
    }
}
