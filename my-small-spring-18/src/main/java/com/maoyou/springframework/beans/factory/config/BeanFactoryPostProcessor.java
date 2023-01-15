package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @ClassName BeanFactoryPostProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/26 15:45
 * @Version 1.0
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
