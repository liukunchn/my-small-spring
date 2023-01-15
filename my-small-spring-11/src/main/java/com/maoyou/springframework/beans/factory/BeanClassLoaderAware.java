package com.maoyou.springframework.beans.factory;

/**
 * @ClassName BeanClassLoaderAware
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 13:48
 * @Version 1.0
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}
