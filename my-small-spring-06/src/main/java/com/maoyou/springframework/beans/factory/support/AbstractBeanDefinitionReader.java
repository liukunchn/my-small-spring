package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.core.io.DefaultResourceLoader;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.io.ResourceLoader;

import java.io.InputStream;

/**
 * @ClassName AbstractBeanDefinitionReader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 12:17
 * @Version 1.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanFactory beanFactory;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.resourceLoader = new DefaultResourceLoader();
    }

    @Override
    public BeanFactory getRegistry() {
        return this.beanFactory;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
        for (Resource resource: resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeanDefinitionStoreException {
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }
}
