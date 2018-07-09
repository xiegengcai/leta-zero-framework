package cn.leta.zero.dto;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-14.
 * @author Xie Gengcai
 */
public class CommonResult implements Serializer {
    @Override
    public byte[] serialize() throws IOException {
        return new byte[0];
    }
}
