package com.maoyou.springframework.context;

import java.util.EventListener;

/**
 * @ClassName ApplicationListener
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:23
 * @Version 1.0
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
