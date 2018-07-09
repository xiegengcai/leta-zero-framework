package cn.leta.zero;

import cn.leta.zero.annotation.ObsoletedType;
import cn.leta.zero.annotation.SPI;
import cn.leta.zero.annotation.ZeroBean;
import cn.leta.zero.annotation.ZeroFilter;
import cn.leta.zero.conf.BaseZeroConf;
import cn.leta.zero.exception.ZeroException;
import cn.leta.zero.filter.AbstractFilter;
import cn.leta.zero.filter.ZeroFilterValue;
import cn.leta.zero.serialize.Serialization;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/26.
 * @author Xie Gengcai
 * @author Xie Gengcai
 */
@Component
public class ZeroContext implements BeanPostProcessor, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(ZeroContext.class);
    private Map<String, SpiMethodHandler> spiMethodHandlerMap;
    private TreeSet<ZeroFilterValue> filters;
    private static Map<Integer, Serialization> serializationMap;
    private static ConfigurableApplicationContext applicationContext;

    @Autowired
    private BaseZeroConf baseZeroConf;


    public ZeroContext() {
        this.spiMethodHandlerMap = new HashMap<>();
        serializationMap = new HashMap<>();
        this.filters = new TreeSet<>();
    }

    @PostConstruct
    public void init() throws Exception {
        String supportedSerializations = baseZeroConf.supportedSerialization();
        if (!StringUtils.hasText(supportedSerializations)) {
            supportedSerializations = "cn.leta.zero.serialize.Hessian2Serialization";
        }
        String [] serializationArray = supportedSerializations.split(";");

        int index = 0;
        for (String serialization : serializationArray) {
            serializationMap.put(index++, (Serialization) Class.forName(serialization).newInstance());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ZeroContext.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (AnnotationUtils.findAnnotation(bean.getClass(), ZeroBean.class) != null) {
            ReflectionUtils.doWithMethods(bean.getClass(),
                    method -> {

                        //ZeroBean 方法的注解
                        SPI spi = AnnotationUtils.findAnnotation(method, SPI.class);
                        //方法注解上的值
                        SpiMethodValue spiMethodValue = new SpiMethodValue(spi.cmd(), spi.version()
                                , ObsoletedType.isObsoleted(spi.obsoleted()));
                        //处理方法的类
                        SpiMethodHandler spiMethodHandler = new SpiMethodHandler();
                        //serviceMethodValue
                        spiMethodHandler.setSpiMethodValue(spiMethodValue);
                        //handler
                        spiMethodHandler.setHandler(bean);
                        //method
                        spiMethodHandler.setHandlerMethod(method);
                        String handlerKey = buildeHandlerKey(spiMethodValue.getCmd(), spiMethodValue.getVersion());
                        //判断重复
                        if (spiMethodHandlerMap.get(handlerKey) != null) {
                            throw new ZeroException(
                                    new StringBuilder("重复的指令， ").append(handlerKey).toString());
                        }
                        // 判断返回类型
//                        if (!ClassUtils.isAssignable(MessageLite.class, method.getReturnType())) {
//                            throw new ZeroException("返回类型只能是MessageLite及其子类");
//                        }

                        // 方法参数支持四种模式
                        // 1. 无参
                        // 2. 一个参数request sequenceId或ChannelHandlerContext或Object
                        // 3. 两个参数request sequenceId、ChannelHandlerContext或request sequenceId、Object
                        // 4. 三个参数request sequenceId、ChannelHandlerContext、Object，固定顺序

                        int parameterCount = method.getParameterCount();
                        if (method.getParameterCount() > 3) {
                            throw new ZeroException(String.format("%s#%s最多包含三个个参数", method.getDeclaringClass().getCanonicalName(), method.getName()));
                        } else {
                            // 一个参数可能是request sequenceId或ChannelHandlerContext或Object
                            if (parameterCount == 1) {
                                if (!(ClassUtils.isAssignable(long.class, method.getParameterTypes()[0])
                                        || ClassUtils.isAssignable(ChannelHandlerContext.class, method.getParameterTypes()[0])
                                        || ClassUtils.isAssignable(Object.class, method.getParameterTypes()[0]))) {
                                    throw new ZeroException(String.format("允许%s#%s(long)、(Object)或(ChannelHandlerContext）", method.getDeclaringClass().getCanonicalName(), method.getName()));
                                }
                            }
                            // 两个参数，第一个参数要么是request sequenceId要么ChannelHandlerContext
                            else if (parameterCount == 2) {
                                boolean fail = true;
                                if (ClassUtils.isAssignable(long.class, method.getParameterTypes()[0])
                                        && (ClassUtils.isAssignable(ChannelHandlerContext.class, method.getParameterTypes()[1])
                                        || ClassUtils.isAssignable(Object.class, method.getParameterTypes()[1]))) {
                                    fail = false;
                                }
                                if (ClassUtils.isAssignable(ChannelHandlerContext.class, method.getParameterTypes()[0])
                                        && (ClassUtils.isAssignable(long.class, method.getParameterTypes()[1])
                                        || ClassUtils.isAssignable(Object.class, method.getParameterTypes()[1]))) {
                                    fail = false;
                                }
                                if (fail) {
                                    throw new ZeroException(String.format("允许%s#%s(long或ChannelHandlerContext)、Object", method.getDeclaringClass().getCanonicalName(), method.getName()));
                                }
                            } else if (parameterCount == 3) {
                                if (!ClassUtils.isAssignable(long.class, method.getParameterTypes()[0])
                                        || !ClassUtils.isAssignable(ChannelHandlerContext.class, method.getParameterTypes()[1])
                                        || !ClassUtils.isAssignable(Object.class, method.getParameterTypes()[2])
                                        ) {
                                    throw new ZeroException(String.format("允许%s#%s(long)、ChannelHandlerContext、Object）", method.getDeclaringClass().getCanonicalName(), method.getName()));
                                }
                            }
                        }
                        spiMethodHandlerMap.put(handlerKey, spiMethodHandler);
                        logger.info("注册Zero指令[{}{}(指令码#版本号)]", spi.description(),handlerKey);
                    },
                    method -> !method.isSynthetic() && AnnotationUtils.findAnnotation(method, SPI.class) != null
            );
        }
        // 扫描过滤器
        ZeroFilter zeroFilter = AnnotationUtils.findAnnotation(bean.getClass(), ZeroFilter.class);
        if (zeroFilter != null) {
            System.out.println(String.format("增加过滤器%s", bean.getClass()));
            this.filters.add(new ZeroFilterValue(zeroFilter.order(), zeroFilter.cmds(), zeroFilter.ignoreCmds(), (AbstractFilter) bean));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public SpiMethodHandler getSpiMethodHandler(int cmd, int version) {
        String handlerKey = buildeHandlerKey(cmd, version);
        SpiMethodHandler handler = spiMethodHandlerMap.get(handlerKey);
        if (handler == null) {
            throw new ZeroException(handlerKey+" 请求指令无效");
        }
        return handler;
    }

    public TreeSet<ZeroFilterValue> getFilters() {
        return filters;
    }

    private String buildeHandlerKey(int cmd, int version) {
        return new StringBuilder().append(cmd).append("#").append(version).toString();
    }

    public static void close(){
        applicationContext.close();
    }
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static Serialization getSerialization(int index) {
        Serialization serialization = serializationMap.get(index);
        if (serialization == null) {
            throw new ZeroException("不支持的序列化方式");
        }
        return serialization;
    }
}
