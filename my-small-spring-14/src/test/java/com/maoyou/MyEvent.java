package com.maoyou;

import com.maoyou.springframework.context.ApplicationEvent;

/**
 * @ClassName MyEvent
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 16:53
 * @Version 1.0
 */
public class MyEvent extends ApplicationEvent {
    public MyEvent(Object source) {
        super(source);
    }
}
