package cn.leta.zero.serialize;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2017/10/24.
 * @author Xie Gengcai
 */
public interface Serialization {
    /**
     * 将对象序列化成字节数组
     * @param t
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> byte[] serialize(T t) throws IOException;

    /**
     * 将字节数组反序列化对象
     * @param bytes
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException;
}
