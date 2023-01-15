package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.*;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.DependencyDescriptor;
import com.maoyou.springframework.core.ResolvableType;
import com.maoyou.springframework.util.Assert;
import com.maoyou.springframework.util.ClassUtils;
import com.maoyou.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    private AutowireCandidateResolver autowireCandidateResolver = SimpleAutowireCandidateResolver.INSTANCE;

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

    public void setAutowireCandidateResolver(AutowireCandidateResolver autowireCandidateResolver) {
        Assert.notNull(autowireCandidateResolver, "AutowireCandidateResolver must not be null");
        if (autowireCandidateResolver instanceof BeanFactoryAware) {
            ((BeanFactoryAware) autowireCandidateResolver).setBeanFactory(this);
        }
        this.autowireCandidateResolver = autowireCandidateResolver;
    }

    public AutowireCandidateResolver getAutowireCandidateResolver() {
        return this.autowireCandidateResolver;
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor, String requestingBeanName, Set<String> autowiredBeanNames) {
        Object result = null;
        if (result == null) {
            result = doResolveDependency(descriptor, requestingBeanName, autowiredBeanNames);
        }
        return result;
    }

    private Object doResolveDependency(DependencyDescriptor descriptor, String beanName, Set<String> autowiredBeanNames) {
        Class<?> type = descriptor.getDependencyType();
        // 对@Value进行处理
        Object value = getAutowireCandidateResolver().getSuggestedValue(descriptor);
        if (value != null) {
            if (value instanceof String) {
                String strVal = resolveEmbeddedValue((String) value);
                value = strVal;
            }
            return value;
        }

        // 对@Autowired进行处理
        Map<String, Object> matchingBeans = findAutowireCandidates(beanName, type, descriptor);
        // 如果不存在可用的bean
        if (matchingBeans.isEmpty()) {
            if (getAutowireCandidateResolver().isRequired(descriptor)) {
                raiseNoMatchingBeanFound(type, null, descriptor);
            }
            return null;
        }

        // 如果存在可用的bean
        String autowiredBeanName = null;
        Object instanceCandidate = null;
        if (matchingBeans.size() > 1) {
            // TODO 如果有多个可用的bean，从中选一个。先选标注了@Primary的，然后选优先级高的
        } else {
            // 如果只有一个可用的bean
            Map.Entry<String, Object> entry = matchingBeans.entrySet().iterator().next();
            autowiredBeanName = entry.getKey();
            instanceCandidate = entry.getValue();
        }
        if (autowiredBeanNames != null) {
            autowiredBeanNames.add(autowiredBeanName);
        }
        if (instanceCandidate instanceof Class) {
            instanceCandidate = descriptor.resolveCandidate(autowiredBeanName, type, this);
        }
        Object result = instanceCandidate;
        if (!ClassUtils.isAssignableValue(type, result)) {
            throw new BeanNotOfRequiredTypeException(autowiredBeanName, type, instanceCandidate.getClass());
        }
        return result;
    }

    private void raiseNoMatchingBeanFound(Class<?> type, ResolvableType resolvableType, DependencyDescriptor descriptor) {
        throw new NoSuchBeanDefinitionException(type.toString());
    }

    private Map<String, Object> findAutowireCandidates(String beanName, Class<?> type, DependencyDescriptor descriptor) {
        Map<String, ?> beansOfType = this.getBeansOfType(type);
        String[] candidateNames = beansOfType.keySet().toArray(new String[0]);
        Map<String, Object> result = CollectionUtils.newLinkedHashMap(candidateNames.length);
        for (String candidate : candidateNames) {
            // 如果不是自身引用，而且是候选者，添加到候选者map，如果存在单例则添加bean实例，否则添加bean的Class类型
            if (!isSelfReference(beanName, candidate) && isAutowireCandidate(candidate, descriptor)) {
                addCandidateEntry(result, candidate, descriptor);
            }
        }
        return result;
    }

    private boolean isSelfReference(String beanName, String candidateName) {
        return (beanName != null && candidateName != null && beanName.equals(candidateName));
    }

    private boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor) {
        return isAutowireCandidate(beanName, descriptor, getAutowireCandidateResolver());
    }

    private boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor, AutowireCandidateResolver resolver) {
        if (containsBeanDefinition(beanName)) {
            return isAutowireCandidate(beanName, getBeanDefinition(beanName), descriptor, resolver);
        } else {
            return true;
        }
    }

    protected boolean isAutowireCandidate(String beanName, BeanDefinition bd,
                                          DependencyDescriptor descriptor, AutowireCandidateResolver resolver) {
        resolveBeanClass(bd, beanName);
        return resolver.isAutowireCandidate(bd, descriptor);
    }

    private void addCandidateEntry(Map<String, Object> candidates, String candidateName,
                                   DependencyDescriptor descriptor) {
        if (containsSingleton(candidateName)) {
            Object beanInstance = descriptor.resolveCandidate(candidateName, null, this);
            candidates.put(candidateName, beanInstance);
        } else {
            candidates.put(candidateName, getType(candidateName));
        }
    }



}
