package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.factory.BeanFactory;

import java.util.Set;

/**
 * @ClassName AutowireCapableBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 16:57
 * @Version 1.0
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor, String requestingBeanName, Set<String> autowiredBeanNames);
}
