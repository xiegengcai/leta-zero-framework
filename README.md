# ZERO框架设计
##多协议支持
1.  [默认协议](leta-default-server/协议设计.md)
## Service协约
```java
@ZeroBean // 类注解
public class HelloService {
    
    // 无参方法
    @SPI(cmd=10)//方法注解
    public Object sayHello(){
        return "Hello World!";
    }

    // 一个参数方法有三种形式
    @SPI(cmd=20)
    public Object sayHello(long seqId){
        return "Hello World!";
    }

    @SPI(cmd=21)
    public Object sayHello(ChannelHandlerContext ctx){
        return "Hello World!";
    }

    @SPI(cmd=22)
    public Object sayHello(String name){
        return "Hello "+name+"!";
    }

    // 两个参数方法有四种形式。即第一个参数要么是long要么是ChannelHandlerContext
    @SPI(cmd=30)
    public Object sayHello(long seqId, String name){
        return "Hello "+name+"!";
    }


    @SPI(cmd=31)
    public Object sayHello(long seqId, ChannelHandlerContext ctx){
        return "Hello World!";
    }

    @SPI(cmd=32)
    public Object sayHello(ChannelHandlerContext ctx, String name){
        return "Hello "+name+"!";
    }

    @SPI(cmd=33)
    public Object sayHello(ChannelHandlerContext ctx, long seqId){
        return "Hello World!";
    }


    // 三个参数固定顺序
    @SPI(cmd=40)
    public Object sayHello(long seqId, ChannelHandlerContext ctx, String name){
        return "Hello World!";
    }
}
```