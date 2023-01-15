package com.maoyou.springframework.context.annotation;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.support.AnnotationBeanNameGenerator;
import com.maoyou.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.maoyou.springframework.beans.factory.support.BeanNameGenerator;
import com.maoyou.springframework.core.annotation.AnnotatedElementUtils;
import com.maoyou.springframework.core.annotation.AnnotationAttributes;
import com.maoyou.springframework.core.env.Environment;
import com.maoyou.springframework.core.io.ResourceLoader;
import com.maoyou.springframework.util.Assert;
import com.maoyou.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @ClassName ClassPathBeanDefinitionScanner
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/3 11:00
 * @Version 1.0
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private final BeanDefinitionRegistry registry;

    private BeanNameGenerator beanNameGenerator = AnnotationBeanNameGenerator.INSTANCE;

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    private boolean includeAnnotationConfig = true;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters,
                                          Environment environment, ResourceLoader resourceLoader) {

        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        this.registry = registry;

        if (useDefaultFilters) {
            registerDefaultFilters();
        }
//        setEnvironment(environment);
        setResourceLoader(resourceLoader);
    }

    public Set<BeanDefinition> doScan(String[] basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
                candidate.setScope(scopeMetadata.getScopeName());
                String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
                if (checkCandidate(beanName, candidate)) {
                    beanDefinitions.add(candidate);
                    registry.registerBeanDefinition(beanName, candidate);
                }
            }
        }
        return beanDefinitions;
    }

    private boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (!Arrays.asList(this.registry.getBeanDefinitionNames()).contains(beanName)) {
            return true;
        }
        throw new RuntimeException("Annotation-specified bean name '" + beanName +
                "' for bean class [" + beanDefinition.getBeanClassName() + "] conflicts with existing, " +
                "non-compatible bean definition of same name and class ["  + "]");
    }
}
