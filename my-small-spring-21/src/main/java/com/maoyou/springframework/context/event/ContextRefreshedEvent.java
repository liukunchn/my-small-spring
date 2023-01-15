package com.maoyou.springframework.context.event;

import com.maoyou.springframework.context.ApplicationContext;

/**
 * @ClassName ContextRefreshedEvent
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:18
 * @Version 1.0
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {
    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }
}
