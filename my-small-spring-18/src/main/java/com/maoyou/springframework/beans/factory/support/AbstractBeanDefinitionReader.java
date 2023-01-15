package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.io.ResourceLoader;
import com.maoyou.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @ClassName AbstractBeanDefinitionReader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 12:17
 * @Version 1.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        if (this.registry instanceof ResourceLoader) {
            this.resourceLoader = (ResourceLoader) this.registry;
        }
        else {
            this.resourceLoader = new PathMatchingResourcePatternResolver();
        }
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
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
