package cn.leta.zero.dto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * 服务端推送
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-21.
 * @author Xie Gengcai
 */
public class ServerPushRequest implements Serializer {
    /**
     * 长度
     */
    private byte length;
    /**
     * 服务器标识
     */
    private int serverId;
    /**
     * 内容
     */
    private byte [] content;
    /**
     * 预留扩展位
     */
    private byte [] extend;
    @Override
    public byte[] serialize() throws IOException {
        this.length = 4;
        if (this.content != null) {
            this.length += this.content.length;
        }
        if (this.extend != null) {
            this.length += this.extend.length;
        }
        ByteBuf byteBuf = Unpooled.buffer();
        // 1 byte
        byteBuf.writeByte(this.length);
        // 4 bytes
        byteBuf.writeInt(this.serverId);
        byteBuf.writeBytes(this.content);
        byteBuf.writeBytes(this.extend);
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getExtend() {
        return extend;
    }

    public void setExtend(byte[] extend) {
        this.extend = extend;
    }
}
