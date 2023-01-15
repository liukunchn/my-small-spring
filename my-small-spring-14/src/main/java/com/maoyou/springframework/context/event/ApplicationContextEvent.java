package com.maoyou.springframework.context.event;

import com.maoyou.springframework.context.ApplicationEvent;
import com.maoyou.springframework.context.support.AllInOneApplicationContext;

/**
 * @ClassName ApplicationContextEvent
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:14
 * @Version 1.0
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(AllInOneApplicationContext source) {
        super(source);
    }

    public AllInOneApplicationContext getApplicationContext() {
        return (AllInOneApplicationContext) getSource();
    }
}
