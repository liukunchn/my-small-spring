package com.maoyou.springframework.context;

import java.time.Clock;
import java.util.EventObject;

/**
 * @ClassName ApplicationEvent
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:10
 * @Version 1.0
 */
public abstract class ApplicationEvent extends EventObject {

    private final long timestamp;

    public ApplicationEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    public ApplicationEvent(Object source, Clock clock) {
        super(source);
        this.timestamp = clock.millis();
    }

    public final long getTimestamp() {
        return timestamp;
    }
}
