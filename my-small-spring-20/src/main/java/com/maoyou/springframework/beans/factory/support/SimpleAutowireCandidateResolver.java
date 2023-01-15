package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.DependencyDescriptor;

/**
 * @ClassName SimpleAutowireCandidateResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/21 16:15
 * @Version 1.0
 */
public class SimpleAutowireCandidateResolver implements AutowireCandidateResolver {
    public static final SimpleAutowireCandidateResolver INSTANCE = new SimpleAutowireCandidateResolver();

    @Override
    public boolean isAutowireCandidate(BeanDefinition bd, DependencyDescriptor descriptor) {
        return bd.isAutowireCandidate();
    }

    @Override
    public boolean isRequired(DependencyDescriptor descriptor) {
        return descriptor.isRequired();
    }

    @Override
    public boolean hasQualifier(DependencyDescriptor descriptor) {
        return false;
    }

    @Override
    public Object getSuggestedValue(DependencyDescriptor descriptor) {
        return null;
    }
}
