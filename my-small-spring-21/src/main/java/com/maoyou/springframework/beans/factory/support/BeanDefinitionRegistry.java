package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;

/**
 * @ClassName BeanDefinitionRegistry
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 17:05
 * @Version 1.0
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException;

    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    String[] getBeanDefinitionNames();
}
