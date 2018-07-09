package cn.leta.zero.dto;

import cn.leta.common.utils.LongLatUtils;
import cn.leta.zero.Gt06Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-21.
 * @author Xie Gengcai
 */
public class GpsLbaStateRequest extends AbstractRequest {
    /**
     * GPS信息长度
     */
    private byte gpsMsgLength;
    /**
     * 卫星数
     */
    private byte satelliteNum;
    /**
     * 经度
     */
    private double longitude;
    /**
     * 纬度
     */
    private double latitude;
    /**
     * 速度 0~255 无符号 byte
     */
    private short speed;
    /**
     * 是否差分GPS Difference GPS
     */
    private boolean dgps;
    /**
     * GPS是否已定位
     */
    private boolean located;
    /**
     * 西经/东经
     */
    private boolean west;
    /**
     * 北纬/南纬
     */
    private boolean north;

    private byte lbsMsgLength;
    private short mcc;
    private byte mnc;
    private short lac;
    private int cellId;

    /**
     * 是否设防
     */
    private boolean defence;
    /**
     *  ACC 高？
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
    private boolean located2;
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
        // 读取日期时间
        setDateTime(Gt06Util.readDate(byteBuf));
        byte[] msgHeader = Gt06Util.splitTwoByte(byteBuf.readByte());
        this.gpsMsgLength = msgHeader[0];
        this.satelliteNum = msgHeader[1];
        this.longitude = LongLatUtils.checkLongitude(byteBuf.readInt() / LongLatUtils.RATIO);
        this.latitude = LongLatUtils.checkLatitude(byteBuf.readInt() / LongLatUtils.RATIO);
        this.speed = byteBuf.readUnsignedByte();
        // 航向及状态
        // 暂无_暂无_差分/实时_是否已定位_西/东经_北/南纬_航向(10bit)
        short courseAndState = byteBuf.readShort();
        this.dgps = (courseAndState & 0B0010_0000_0000_0000) > 0;
        this.located = (courseAndState & 0B0001_0000_0000_0000) > 0;
        this.west = (courseAndState & 0B0000_1000_0000_0000) > 0;
        this.north = (courseAndState & 0B0000_0100_0000_0000) > 0;
        int courseVal = courseAndState & 0B0000_00_11_1111_1111;
        // TODO 如何将该值转换？
        // 重算经纬度正负值
        this.longitude = LongLatUtils.convertLonDAndStrToD(this.longitude, this.west);
        this.latitude = LongLatUtils.convertLatDAndStrToD(this.latitude, this.north);

        // 跳过GPS预览位
        byteBuf.skipBytes(gpsMsgLength - 12);

        this.lbsMsgLength = byteBuf.readByte();
        this.mcc = byteBuf.readShort();
        this.mnc = byteBuf.readByte();
        this.lac = byteBuf.readShort();
        // cell 3bytes 0x000000 ~ 0xFFFFFF
        int first = byteBuf.readByte() & 0xFF0000;
        this.cellId = first + byteBuf.readShort();

        // 跳过LBS预览位
        byteBuf.skipBytes(lbsMsgLength - 9);

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

    public byte getGpsMsgLength() {
        return gpsMsgLength;
    }

    public void setGpsMsgLength(byte gpsMsgLength) {
        this.gpsMsgLength = gpsMsgLength;
    }

    public byte getSatelliteNum() {
        return satelliteNum;
    }

    public void setSatelliteNum(byte satelliteNum) {
        this.satelliteNum = satelliteNum;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    public boolean isDgps() {
        return dgps;
    }

    public void setDgps(boolean dgps) {
        this.dgps = dgps;
    }

    public boolean isLocated() {
        return located;
    }

    public void setLocated(boolean located) {
        this.located = located;
    }

    public boolean isWest() {
        return west;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public boolean isNorth() {
        return north;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public byte getLbsMsgLength() {
        return lbsMsgLength;
    }

    public void setLbsMsgLength(byte lbsMsgLength) {
        this.lbsMsgLength = lbsMsgLength;
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

    public boolean isLocated2() {
        return located2;
    }

    public void setLocated2(boolean located2) {
        this.located2 = located2;
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
