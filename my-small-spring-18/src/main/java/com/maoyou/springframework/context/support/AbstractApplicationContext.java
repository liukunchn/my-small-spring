package com.maoyou.springframework.context.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import com.maoyou.springframework.context.ApplicationEvent;
import com.maoyou.springframework.context.ApplicationListener;
import com.maoyou.springframework.context.ConfigurableApplicationContext;
import com.maoyou.springframework.context.event.ApplicationEventMulticaster;
import com.maoyou.springframework.context.event.ContextClosedEvent;
import com.maoyou.springframework.context.event.ContextRefreshedEvent;
import com.maoyou.springframework.context.event.SimpleApplicationEventMulticaster;
import com.maoyou.springframework.core.convert.ConversionService;
import com.maoyou.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.Map;

/**
 * @ClassName AbstractApplicationContext
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 17:43
 * @Version 1.0
 */
public abstract class AbstractApplicationContext extends PathMatchingResourcePatternResolver implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private Thread shutdownHook;

    private ApplicationEventMulticaster applicationEventMulticaster;

    public void refresh() throws BeansException, IllegalStateException {
        // 获取beanFactory
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        // 准备BeanFactory（注册ApplicationContextAwareProcessor）
        prepareBeanFactory(beanFactory);
        try {
            // 调用BeanFactoryPostProcessor
            invokeBeanFactoryPostProcessors(beanFactory);
            // 注册BeanPostProcessor
            registerBeanPostProcessors(beanFactory);
            // 初始化事件广播器
            initApplicationEventMulticaster();
            // 注册监听器
            registerListeners();
            // 预实例化单例对象
            finishBeanFactoryInitialization(beanFactory);
            // 完成刷新——发布上下文刷新事件
            finishRefresh();
        } catch (BeansException e) {
            throw e;
        }
    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        beanFactoryPostProcessorMap.values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        beanPostProcessorMap.values().forEach(beanPostProcessor -> {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });
    }

    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
    }

    protected void registerListeners() {
        Map<String, ApplicationListener> listenerMap = getBeansOfType(ApplicationListener.class);
        listenerMap.forEach((name, listener) -> {
            this.applicationEventMulticaster.addApplicationListener(listener);
        });
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        // 设置类型转换器
        if (beanFactory.containsBean("conversionService")) {
            beanFactory.setConversionService(beanFactory.getBean("conversionService", ConversionService.class));
        }
        // 预先实例化单例
        beanFactory.preInstantiateSingletons();
    }

    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void registerShutdownHook() {
        if (this.shutdownHook == null) {
            this.shutdownHook = new Thread("SpringContextShutdownHook") {
                @Override
                public void run() {
                    doClose();
                }
            };
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }
    }

    @Override
    public void close() {
        doClose();
        if (this.shutdownHook != null) {
            Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
        }
    }

    protected void doClose() {
        publishEvent(new ContextClosedEvent(this));
        getBeanFactory().destroySingletons();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requriedType) throws BeansException {
        return getBeanFactory().getBean(name,requriedType);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }
}

