package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.PropertyValues;

/**
 * @ClassName InstantiationAwareBeanPostProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/13 16:28
 * @Version 1.0
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在doCreateBean()方法之前执行
     * 如果返回值不为null，则doCreateBean()短路
     * 如果返回值不为null，可以调用BeanPostProcessors.postProcessAfterInitialization()方法
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 在populateBean()中postProcessProperties(),applyPropertyValues()之前执行
     * 如果返回false，则postProcessProperties(),applyPropertyValues()短路
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 在populateBean()中applyPropertyValues()之前执行
     * 如果返回null，将调用废弃的postProcessPropertyValues()方法默认返回原来的pvs
     * 如果返回不为null，将使用返回值覆盖原来的pvs
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        return pvs;
    }

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
