package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.maoyou.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultListableBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 19:25
 * @Version 1.0
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDifinitionMap = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = beanDifinitionMap.get(beanName);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(beanName);
        }
        return bd;
    }

    @Override
    protected boolean containsBeanDefinition(String name) {
        return beanDifinitionMap.containsKey(name);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDifinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> map = new HashMap<>();
        beanDifinitionMap.forEach((beanName, beanDefinition) -> {
            resolveBeanClass(beanDefinition, beanName);
            if (type.isAssignableFrom(beanDefinition.getBeanClass())) {
                map.put(beanName, getBean(beanName, type));
            }
        });
        return map;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
        beanDifinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDifinitionMap.forEach((beanName, bd) -> {
            if (bd.isSingleton()) {
                getBean(beanName);
            }
        });
    }
}
