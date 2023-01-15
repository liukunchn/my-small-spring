package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.BeansException;

/**
 * @ClassName BeanPostProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/26 15:46
 * @Version 1.0
 */
public interface BeanPostProcessor {
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
