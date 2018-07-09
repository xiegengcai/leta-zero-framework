package cn.leta.zero.dto;

import java.time.LocalDateTime;

/**
 * Created by <a href="mailto:xiegengcai@foxmail.com">xiegengcai</a> on 2017-12-19.
 * @author Xie Gengcai
 */
public abstract class AbstractRequest implements Deserializer {
    protected LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
