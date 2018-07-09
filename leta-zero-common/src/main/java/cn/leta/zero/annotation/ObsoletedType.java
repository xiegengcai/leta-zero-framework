package cn.leta.zero.annotation;

/**
 * 服务方法是否已经过期，过期的服务方法不能再访问
 * Created by <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a> on 2016/8/26.
 * @author Xie Gengcai
 */
public enum ObsoletedType {
    YES, NO;

    public static boolean isObsoleted(ObsoletedType type) {
        if (YES == type) {
            return true;
        } else {
            return false;
        }
    }
}
