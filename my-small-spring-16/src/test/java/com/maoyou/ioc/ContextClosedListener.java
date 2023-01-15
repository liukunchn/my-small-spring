package com.maoyou.ioc;

import com.maoyou.springframework.context.ApplicationListener;
import com.maoyou.springframework.context.event.ContextClosedEvent;

/**
 * @ClassName ContextClosedListener
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 16:49
 * @Version 1.0
 */
public class ContextClosedListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("容器关闭："+event);
    }
}
