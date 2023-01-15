package com.maoyou.springframework.context.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import com.maoyou.springframework.context.ApplicationContextAware;

/**
 * @ClassName ApplicationContextAwareProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 14:04
 * @Version 1.0
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final AllInOneApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(AllInOneApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
        }
        return bean;
    }
}
