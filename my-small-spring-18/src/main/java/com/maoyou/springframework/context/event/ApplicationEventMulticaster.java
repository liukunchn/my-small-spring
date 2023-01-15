package com.maoyou.springframework.context.event;

import com.maoyou.springframework.context.ApplicationEvent;
import com.maoyou.springframework.context.ApplicationListener;

/**
 * @ClassName ApplicationEventMulticaster
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:31
 * @Version 1.0
 */
public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);
    void removeApplicationListener(ApplicationListener<?> listener);
    void multicastEvent(ApplicationEvent event);
}
