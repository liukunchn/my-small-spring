package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.CannotLoadBeanClassException;
import com.maoyou.springframework.beans.factory.DisposableBean;
import com.maoyou.springframework.beans.factory.FactoryBean;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import com.maoyou.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.maoyou.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.maoyou.springframework.core.convert.ConversionService;
import com.maoyou.springframework.util.Assert;
import com.maoyou.springframework.util.ClassUtils;
import com.maoyou.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName AbstractBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 17:39
 * @Version 1.0
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    private ConversionService conversionService;
    private final List<StringValueResolver> embeddedValueResolvers = new CopyOnWriteArrayList<>();
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null);
    }
    @Override
    public <T> T getBean(String name, Class<T> requriedType) throws BeansException {
        return doGetBean(name, requriedType, null);
    }
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, null, args);
    }

    protected   <T> T doGetBean(String name, Class<T> requriedType, Object... args) throws BeansException {
        Object bean;
        // 如果单例容器中有，从单例容器中获取
        Object beanInstance = getSingleton(name);
        if (beanInstance != null) {
            bean = getObjectForBeanInstance(beanInstance, name);
            return (T) bean;
        }
        BeanDefinition bd = getBeanDefinition(name);
        beanInstance = createBean(name, bd, args);
        // 缓存单例
        if (bd.isSingleton()) {
            registerSingleton(name, beanInstance);
        }
        bean = getObjectForBeanInstance(beanInstance, name);
        return (T) bean;
    }

    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object object = getCachedObjectForFactoryBean(beanName);
        if (object == null) {
            object = getObjectFromFactoryBean((FactoryBean<?>) beanInstance, beanName);
        }
        return object;
    }

    protected Class<?> resolveBeanClass(BeanDefinition bd, String beanName) throws CannotLoadBeanClassException {
        Class<?> beanClass;
        try {
            beanClass = bd.resolveBeanClass(null);
        } catch (ClassNotFoundException e) {
            throw new CannotLoadBeanClassException(beanName, bd.getBeanClassName(), e);
        }
        return beanClass;
    }

    protected abstract Object createBean(String beanName, BeanDefinition bd, Object[] args) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition bd) {
        if (bd.isSingleton() && (bean instanceof DisposableBean || bd.getDestroyMethodName() != null)) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, bd));
        }
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public ConversionService getConversionService() {
        return this.conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public boolean containsBean(String name) {
        return containsBeanDefinition(name);
    }

    protected boolean hasInstantiationAwareBeanPostProcessors() {
        return !getInstantiationAwareBeanPostProcessors().isEmpty();
    }

    protected List<InstantiationAwareBeanPostProcessor> getInstantiationAwareBeanPostProcessors() {
        List<InstantiationAwareBeanPostProcessor> list = new ArrayList<>();
        for (BeanPostProcessor bp : this.beanPostProcessors) {
            if (bp instanceof InstantiationAwareBeanPostProcessor) {
                list.add((InstantiationAwareBeanPostProcessor) bp);
            }
        }
        return list;
    }

    protected Class<?> getType(String name) {
        BeanDefinition bd = getBeanDefinition(name);
        try {
            bd.resolveBeanClass(ClassUtils.getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bd.getBeanClass();
    }

    protected abstract boolean containsBeanDefinition(String name);

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        Assert.notNull(valueResolver, "StringValueResolver must not be null");
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        if (value == null) {
            return null;
        }
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
            if (result == null) {
                return null;
            }
        }
        return result;
    }
}
