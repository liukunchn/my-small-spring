package com.maoyou.springframework.context.event;

import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.maoyou.springframework.context.ApplicationEvent;
import com.maoyou.springframework.context.ApplicationListener;

import java.util.Collection;

/**
 * @ClassName SimpleApplicationEventMulticaster
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 16:01
 * @Version 1.0
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
    public SimpleApplicationEventMulticaster(ConfigurableListableBeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        Collection<ApplicationListener<?>> applicationListeners = getApplicationListeners(event);
        for (ApplicationListener listener : applicationListeners) {
            listener.onApplicationEvent(event);
        }
    }
}
