package com.maoyou.springframework.beans.factory;

/**
 * @ClassName FactoryBean
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 20:13
 * @Version 1.0
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;
    Class<?> getObjectType();
    default boolean isSingleton() {
        return true;
    }
}
