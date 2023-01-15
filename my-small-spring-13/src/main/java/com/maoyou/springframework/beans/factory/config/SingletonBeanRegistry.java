package com.maoyou.springframework.beans.factory.config;

/**
 * @ClassName SingletonBeanRegistry
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 16:35
 * @Version 1.0
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);
}
