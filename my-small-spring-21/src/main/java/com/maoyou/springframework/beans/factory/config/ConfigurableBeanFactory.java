package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.core.convert.ConversionService;
import com.maoyou.springframework.util.StringValueResolver;

/**
 * @ClassName ConfigurableBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 16:46
 * @Version 1.0
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, BeanFactory {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    void destroySingletons();

    ConversionService getConversionService();
    void setConversionService(ConversionService conversionService);

    void addEmbeddedValueResolver(StringValueResolver valueResolver);
    String resolveEmbeddedValue(String value);

}
