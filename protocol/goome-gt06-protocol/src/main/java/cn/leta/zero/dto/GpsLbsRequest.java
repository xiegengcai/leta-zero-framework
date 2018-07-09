package cn.leta.zero.dto;

import cn.leta.common.utils.LongLatUtils;
import cn.leta.zero.Gt06Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * LBS GPS合并信息
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-19.
 * @author Xie Gengcai
 */
public class GpsLbsRequest extends AbstractRequest {
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

    private short mcc;
    private byte mnc;
    private short lac;
    private int cellId;
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

        // 跳过预览位
        byteBuf.skipBytes(gpsMsgLength - 12);

        this.mcc = byteBuf.readShort();
        this.mnc = byteBuf.readByte();
        this.lac = byteBuf.readShort();
        // cell 3bytes 0x000000 ~ 0xFFFFFF
        int first = byteBuf.readByte() & 0xFF0000;
        this.cellId = first + byteBuf.readShort();
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
}
