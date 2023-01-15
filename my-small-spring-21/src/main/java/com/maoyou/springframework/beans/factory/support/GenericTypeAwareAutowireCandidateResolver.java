package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.BeanFactoryAware;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.DependencyDescriptor;

/**
 * @ClassName GenericTypeAwareAutowireCandidateResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/21 16:19
 * @Version 1.0
 */
public class GenericTypeAwareAutowireCandidateResolver extends SimpleAutowireCandidateResolver implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected final BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public boolean isAutowireCandidate(BeanDefinition bd, DependencyDescriptor descriptor) {
        if (!super.isAutowireCandidate(bd, descriptor)) {
            // If explicitly false, do not proceed with any other checks...
            return false;
        }
        return checkGenericTypeMatch(bd, descriptor);
    }

    /**
     * 检查泛型是否匹配，这里直接返回true
     * @param bd
     * @param descriptor
     * @return
     */
    private boolean checkGenericTypeMatch(BeanDefinition bd, DependencyDescriptor descriptor) {
        return true;
    }
}
