package com.maoyou.springframework.context.event;

import com.maoyou.springframework.context.ApplicationContext;
import com.maoyou.springframework.context.support.AllInOneApplicationContext;

/**
 * @ClassName ContextClosedEvent
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:19
 * @Version 1.0
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }
}
