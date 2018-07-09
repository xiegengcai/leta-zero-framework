package cn.leta.zero.dto;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-05.
 * @author Xie Gengcai
 */
public interface Serializer {
    /**
     * 序列化
     * @return
     * @throws IOException
     */
    byte[] serialize() throws IOException;
}
