package cn.leta.zero.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;

/**
 * 对于嵌套场景无法支持
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2017/10/24.
 * @author Xie Gengcai
 */
public class FastJsonSerialization implements Serialization {

    @Override
    public <T> byte[] serialize(T t) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(t);
        return out.toBytes((String) null);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return JSON.parseObject(new String(data), clz);
    }
}
