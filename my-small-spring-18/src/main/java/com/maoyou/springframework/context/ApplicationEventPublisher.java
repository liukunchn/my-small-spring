package com.maoyou.springframework.context;

/**
 * @ClassName ApplicationEventPublisher
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 17:21
 * @Version 1.0
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
