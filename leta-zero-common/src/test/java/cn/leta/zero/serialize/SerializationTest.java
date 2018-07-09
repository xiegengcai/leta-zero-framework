package cn.leta.zero.serialize;

import cn.leta.zero.Message;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2017/10/25.
 */
public class SerializationTest {

    @Test
    public void fastJsonSerializationTest() throws IOException {
        Serialization serialization = new FastJsonSerialization();
        User user = new User();
        user.setAge(20);
        user.setName("test");

        byte[] body = serialization.serialize(user);

        User user1 = serialization.deserialize(body, User.class);

        Assert.assertEquals(user, user1);
    }

    @Test
    public void hessian2SerializationTest()throws IOException{
        Serialization serialization = new Hessian2Serialization();
        User user = new User();
        user.setAge(20);
        user.setName("test");

        byte[] body = serialization.serialize(user);

        User user1 = serialization.deserialize(body, User.class);

        Assert.assertEquals(user, user1);
    }

    @Test
    public void hproseSerializationTest()throws IOException{
        Serialization serialization = new HproseSerialization();
        User user = new User();
        user.setAge(20);
        user.setName("test");

        byte[] body = serialization.serialize(user);

        User user1 = serialization.deserialize(body, User.class);

        Assert.assertEquals(user, user1);
    }

    @Test
    public void protobufSerializationTest() throws IOException {
        Serialization serialization = new ProtobufSerialization();
        Message.User user = Message.User.newBuilder().setAge(20).setName("测试").build();

        byte[] body = serialization.serialize(user);

        Message.User user1 = serialization.deserialize(body, Message.User.class);
        Assert.assertEquals(user, user1);

    }
}
