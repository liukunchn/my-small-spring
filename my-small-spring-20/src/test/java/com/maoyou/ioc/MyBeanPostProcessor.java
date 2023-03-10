package com.maoyou.ioc;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @ClassName MyBeanPostProcessor
 * @Description
 * @Author εε€ kunliu@yinhai.com
 * @Date 2021/9/26 17:13
 * @Version 1.0
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("user".equals(beanName)) {
            ((User) bean).setName("ηδΊ");
        }
        return bean;
    }
}
