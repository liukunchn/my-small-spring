package com.maoyou.springframework.context.event;

import com.maoyou.springframework.context.ApplicationContext;
import com.maoyou.springframework.context.ApplicationEvent;

/**
 * @ClassName ApplicationContextEvent
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:14
 * @Version 1.0
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
    }

    public ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
