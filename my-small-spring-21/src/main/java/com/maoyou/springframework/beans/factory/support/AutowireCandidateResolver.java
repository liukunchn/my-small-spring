package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.DependencyDescriptor;

/**
 * @ClassName AutowireCandidateResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/21 16:08
 * @Version 1.0
 */
public interface AutowireCandidateResolver {

    default boolean isAutowireCandidate(BeanDefinition bd, DependencyDescriptor descriptor) {
        return bd.isAutowireCandidate();
    }


    default boolean isRequired(DependencyDescriptor descriptor) {
        return descriptor.isRequired();
    }


    default boolean hasQualifier(DependencyDescriptor descriptor) {
        return false;
    }

    default Object getSuggestedValue(DependencyDescriptor descriptor) {
        return null;
    }

}
