package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.BeansException;

/**
 * @ClassName ObjectFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/3/9 14:38
 * @Version 1.0
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
