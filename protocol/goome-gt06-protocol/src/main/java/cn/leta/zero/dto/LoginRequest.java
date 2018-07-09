package cn.leta.zero.dto;

import cn.leta.zero.Gt06Util;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-14.
 * @author Xie Gengcai
 */
public class LoginRequest implements Deserializer, Serializer {
    private String imei;

    public LoginRequest() {
    }

    public LoginRequest(String imei) {
        this.imei = imei;
    }

    @Override
    public Deserializer deserialize(byte[] bytes) throws IOException {
        this.imei = Gt06Util.bytes2ImeiStr(bytes);
        return this;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public byte[] serialize() throws IOException {
        return Gt06Util.imeiString2Bytes(this.imei);
    }

}
