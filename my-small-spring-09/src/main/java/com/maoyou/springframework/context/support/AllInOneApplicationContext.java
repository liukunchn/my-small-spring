package com.maoyou.springframework.context.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import com.maoyou.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.maoyou.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.maoyou.springframework.core.io.Resource;

import java.util.Map;

/**
 * @ClassName AllInOneApplicationContext
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/23 19:44
 * @Version 1.0
 */
public class AllInOneApplicationContext {

    private String[] configLocations;

    private Resource[] configResources;

    private DefaultListableBeanFactory beanFactory;

    public AllInOneApplicationContext(String configLocation) throws BeansException {
        this(new String[] {configLocation});
    }

    public AllInOneApplicationContext(String... configLocations) throws BeansException {
        setConfigLocations(configLocations);
        refresh();
    }

    public void setConfigLocations(String... locations) {
        if (locations != null) {
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                this.configLocations[i] = locations[i];
            }
        } else {
            this.configLocations = null;
        }
    }

    public void refresh() throws BeansException, IllegalStateException {
        // 获取beanFactory
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        try {
            // 调用BeanFactoryPostProcessor
            invokeBeanFactoryPostProcessors(beanFactory);
            // 注册BeanPostProcessor
            registerBeanPostProcessors(beanFactory);
            // 预实例化单例对象
            finishBeanFactoryInitialization(beanFactory);
        } catch (BeansException e) {
            throw e;
        }
    }


    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        Resource[] configResources = this.configResources;
        if (configResources != null) {
            reader.loadBeanDefinitions(configResources);
        }

        String[] configLocations = this.configLocations;
        if (configLocations != null) {
            reader.loadBeanDefinitions(configLocations);
        }
    }

    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    public <T> T getBean(String name, Class<T> requriedType) throws BeansException {
        return getBeanFactory().getBean(name,requriedType);
    }

    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }


    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        beanFactoryPostProcessorMap.values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        beanPostProcessorMap.values().forEach(beanPostProcessor -> {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        });
    }



}
