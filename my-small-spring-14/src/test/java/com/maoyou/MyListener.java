package com.maoyou;

import com.maoyou.springframework.context.ApplicationListener;

/**
 * @ClassName MyListener
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 16:54
 * @Version 1.0
 */
public class MyListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("自定义事件：" + event);
    }
}
