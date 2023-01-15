package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @ClassName ConfigurableListableBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 17:00
 * @Version 1.0
 */
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, AutowireCapableBeanFactory, ListableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
}
