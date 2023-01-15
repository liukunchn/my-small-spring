package com.maoyou.ioc;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.PropertyValue;
import com.maoyou.springframework.beans.PropertyValues;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @ClassName MyBeanFactoryPostProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/26 15:54
 * @Version 1.0
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition bd = beanFactory.getBeanDefinition("user");
        PropertyValues propertyValues = bd.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "李四"));
    }
}
