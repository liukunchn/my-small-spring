package com.maoyou.springframework.context.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import com.maoyou.springframework.context.ApplicationContext;
import com.maoyou.springframework.context.ApplicationContextAware;
import com.maoyou.springframework.context.EnvironmentAware;
import com.maoyou.springframework.core.env.Environment;

/**
 * @ClassName ApplicationContextAwareProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 14:04
 * @Version 1.0
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
        }
        if (bean instanceof EnvironmentAware) {
            ((EnvironmentAware) bean).setEnvironment(applicationContext.getEnvironment());
        }
        return bean;
    }
}
