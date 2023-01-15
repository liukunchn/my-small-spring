package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.factory.BeanFactory;

/**
 * @ClassName ConfigurableBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 16:46
 * @Version 1.0
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, BeanFactory {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    void destroySingletons();
}
