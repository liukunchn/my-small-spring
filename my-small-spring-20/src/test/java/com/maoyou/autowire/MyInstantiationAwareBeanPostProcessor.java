package com.maoyou.autowire;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @ClassName MyInstantiationAwareBeanPostProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/19 10:39
 * @Version 1.0
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return new UserDaoImpl2();
    }
}
