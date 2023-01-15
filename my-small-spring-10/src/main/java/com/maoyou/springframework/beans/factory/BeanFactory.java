package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.BeansException;

/**
 * @ClassName BeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 16:44
 * @Version 1.0
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
