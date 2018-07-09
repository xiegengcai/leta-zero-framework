package cn.leta.zero.dto;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-05.
 * @author Xie Gengcai
 */
public interface Deserializer {
    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws IOException
     */
    Deserializer deserialize(byte[] bytes) throws IOException;
}
