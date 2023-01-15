package com.maoyou.ioc;

import com.maoyou.springframework.context.ApplicationListener;
import com.maoyou.springframework.context.event.ContextRefreshedEvent;

/**
 * @ClassName ContextRefreshedListener
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 16:48
 * @Version 1.0
 */
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("容器刷新："+event);
    }
}
