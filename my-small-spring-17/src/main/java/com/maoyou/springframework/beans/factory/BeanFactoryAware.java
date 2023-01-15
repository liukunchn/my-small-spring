package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.BeansException;

/**
 * @ClassName BeanFactoryAware
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 13:45
 * @Version 1.0
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
