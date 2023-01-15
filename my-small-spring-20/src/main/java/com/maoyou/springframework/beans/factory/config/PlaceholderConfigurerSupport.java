package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.*;
import com.maoyou.springframework.util.StringValueResolver;

/**
 * @ClassName PlaceholderConfigurerSupport
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 16:43
 * @Version 1.0
 */
public abstract class PlaceholderConfigurerSupport extends PropertyResourceConfigurer implements BeanFactoryAware, BeanNameAware {
    protected boolean trimValues = false;
    protected String nullValue;
    protected boolean ignoreUnresolvablePlaceholders = false;
    private String beanName;
    private BeanFactory beanFactory;

    public void setTrimValues(boolean trimValues) {
        this.trimValues = trimValues;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) throws BeansException {
        this.beanName = beanName;
    }

    protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess, StringValueResolver valueResolver) {
        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);
        String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
        for (String curName : beanNames) {
            if (!(curName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
                BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);
                try {
                    visitor.visitBeanDefinition(bd);
                } catch (Exception e) {
                    throw new BeanDefinitionStoreException("Invalid bean definition with name '" + beanName + "' defined in " + "resourceDescription" + ": " + e.getMessage(), e);
                }
            }
        }
        // resolveAliases

        // addEmbeddedValueResolver
        beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);
    }
}
