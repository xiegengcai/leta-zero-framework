package cn.leta.zero;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/25.
 * @author Xie Gengcai
 */
public interface Endpoint {

    /**
     * 启动
     * @param args
     * @throws InterruptedException
     */
    void start(String[] args) throws InterruptedException;

    /**
     * 关闭
     */
    void close();

    /**
     * 判断是否已经关闭
     * @return
     */
    boolean isClosed();

    /**
     * 判断是否可用
     * @return
     */
    boolean isAvailable();

    /**
     * 启动Spring Boot
     * @param args
     */
    void startSpringBoot(String[] args);
}
