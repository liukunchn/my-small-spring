package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.core.annotation.AnnotationAttributes;
import com.maoyou.springframework.core.type.AnnotationMetadata;
import com.maoyou.springframework.util.Assert;
import com.maoyou.springframework.util.ClassUtils;
import com.maoyou.springframework.util.StringUtils;

import java.beans.Introspector;
import java.util.Collections;
import java.util.Set;

/**
 * @ClassName AnnotationBeanNameGenerator
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/21 23:39
 * @Version 1.0
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    public static final AnnotationBeanNameGenerator INSTANCE = new AnnotationBeanNameGenerator();

    private static final String COMPONENT_ANNOTATION_CLASSNAME = "org.springframework.stereotype.Component";
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition.getMetadata() != null) {
            String beanName = determineBeanNameFromAnnotation(definition);
            if (StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        // Fallback: generate a unique default bean name.
        return buildDefaultBeanName(definition, registry);
    }

    private String determineBeanNameFromAnnotation(BeanDefinition annotatedDef) {
        AnnotationMetadata amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types) {
            AnnotationAttributes attributes =  AnnotationAttributes.fromMap(amd.getAnnotationAttributes(type, false));
            if (attributes != null) {
                Set<String> metaTypes = amd.getMetaAnnotationTypes(type);
                if (isStereotypeWithNameValue(type, metaTypes, attributes)) {
                    Object value = attributes.get("value");
                    if (value instanceof String) {
                        String strVal = (String) value;
                        if (StringUtils.hasLength(strVal)) {
                            if (beanName != null && !strVal.equals(beanName)) {
                                throw new IllegalStateException("Stereotype annotations suggest inconsistent " +
                                        "component names: '" + beanName + "' versus '" + strVal + "'");
                            }
                            beanName = strVal;
                        }
                    }
                }
            }
        }
        return beanName;
    }

    private boolean isStereotypeWithNameValue(String type, Set<String> annotationType, AnnotationAttributes attributes) {
        boolean isStereotype = type.equals(COMPONENT_ANNOTATION_CLASSNAME) ||
                annotationType.contains(COMPONENT_ANNOTATION_CLASSNAME);
        return (isStereotype && attributes != null && attributes.containsKey("value"));
    }

    private String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanClassName = definition.getBeanClassName();
        Assert.state(beanClassName != null, "No bean class name set");
        String shortClassName = ClassUtils.getShortName(beanClassName);
        return Introspector.decapitalize(shortClassName);
    }
}
