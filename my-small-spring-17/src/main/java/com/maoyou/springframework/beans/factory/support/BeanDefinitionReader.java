package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.io.ResourceLoader;

/**
 * @ClassName BeanDefinitionReader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 12:15
 * @Version 1.0
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();
    ResourceLoader getResourceLoader();
    void loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;
    void loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException;
    void loadBeanDefinitions(String location) throws BeanDefinitionStoreException;
    void loadBeanDefinitions(String... locations) throws BeanDefinitionStoreException;
}
