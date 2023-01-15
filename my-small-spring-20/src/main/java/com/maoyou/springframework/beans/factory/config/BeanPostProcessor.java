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
    /**
     * 在invokeInitMethods()方法之前调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 在invokeInitMethods()方法之后调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
