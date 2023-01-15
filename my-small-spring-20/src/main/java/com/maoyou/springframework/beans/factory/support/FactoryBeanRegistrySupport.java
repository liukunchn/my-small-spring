package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.BeanCreationException;
import com.maoyou.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FactoryBeanRegistrySupport
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 20:15
 * @Version 1.0
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>(16);

    protected Object getCachedObjectForFactoryBean(String beanName) {
        return factoryBeanObjectCache.get(beanName);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
            }
            if (containsSingleton(beanName)) {
                this.factoryBeanObjectCache.put(beanName, object);
            }
            return object;
        } else {
            Object object = doGetObjectFromFactoryBean(factory, beanName);
            return object;
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        Object object;
        try {
            object = factory.getObject();
        } catch (Exception e) {
            throw new BeanCreationException(beanName, "FactoryBean threw exception on object creation", e);
        }
        return object;
    }
}
