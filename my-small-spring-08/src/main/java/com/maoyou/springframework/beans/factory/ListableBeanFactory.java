package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.BeansException;

import java.util.Map;

/**
 * @ClassName ListableBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 16:52
 * @Version 1.0
 */
public interface ListableBeanFactory extends BeanFactory {
    String[] getBeanDefinitionNames();
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
