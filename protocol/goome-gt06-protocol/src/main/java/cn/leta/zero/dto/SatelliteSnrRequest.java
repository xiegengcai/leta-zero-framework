package cn.leta.zero.dto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-21.
 * @author Xie Gengcai
 */
public class SatelliteSnrRequest implements Deserializer{
    private byte satelliteCount;
    private byte[] snrs;

    @Override
    public Deserializer deserialize(byte[] bytes) throws IOException {
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        this.satelliteCount = byteBuf.readByte();
        this.snrs = new byte[this.satelliteCount];
        byteBuf.readBytes(this.snrs);
        return this;
    }

    public byte getSatelliteCount() {
        return satelliteCount;
    }

    public void setSatelliteCount(byte satelliteCount) {
        this.satelliteCount = satelliteCount;
    }

    public byte[] getSnrs() {
        return snrs;
    }

    public void setSnrs(byte[] snrs) {
        this.snrs = snrs;
    }
}
