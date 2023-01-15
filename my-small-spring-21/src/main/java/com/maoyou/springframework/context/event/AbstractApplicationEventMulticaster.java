package com.maoyou.springframework.context.event;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.BeanClassLoaderAware;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.BeanFactoryAware;
import com.maoyou.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.maoyou.springframework.context.ApplicationEvent;
import com.maoyou.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @ClassName AbstractApplicationEventMulticaster
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 15:32
 * @Version 1.0
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanClassLoaderAware, BeanFactoryAware {
    public final Set<ApplicationListener<?>> applicationListeners = new LinkedHashSet<>();
    private ClassLoader beanClassLoader;
    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory) beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        this.applicationListeners.remove(listener);
    }

    protected Collection<ApplicationListener<?>> getApplicationListeners(ApplicationEvent event) {
        Collection<ApplicationListener<?>> allListeners = new ArrayList<>();
        for (ApplicationListener<?> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    private boolean supportsEvent(ApplicationListener<?> listener, ApplicationEvent event) {
        Type actualTypeArgument = ((ParameterizedType) listener.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Class<?> supportedEvent = null;
        try {
            supportedEvent = Class.forName(actualTypeArgument.getTypeName());
        } catch (ClassNotFoundException e) {
            return false;
        }
        return  (supportedEvent.isAssignableFrom(event.getClass()));
    }
}
