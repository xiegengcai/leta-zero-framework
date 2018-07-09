package cn.leta.zero.dto;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-14.
 */
public class LoginRequestTest {

    @Test
    public void test() throws IOException {
        byte [] bytes =new byte[]{1, 23, 45, 67, 89, 01, 23, 45};
        LoginRequest request = new LoginRequest();
        request.deserialize(bytes);
        System.out.println(request.getImei());
        byte [] bytes2 = request.serialize();
        Assert.assertArrayEquals(bytes, bytes2);
        LoginRequest request2 = new LoginRequest();
        request2.deserialize(bytes2);
        Assert.assertEquals(request.getImei(), request2.getImei());
    }
}
