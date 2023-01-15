package com.maoyou.springframework.beans.factory.annotation;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.DependencyDescriptor;
import com.maoyou.springframework.beans.factory.support.GenericTypeAwareAutowireCandidateResolver;
import com.maoyou.springframework.core.MethodParameter;
import com.maoyou.springframework.core.annotation.AnnotatedElementUtils;
import com.maoyou.springframework.core.annotation.AnnotationAttributes;
import com.maoyou.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @ClassName QualifierAnnotationAutowireCandidateResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/21 16:24
 * @Version 1.0
 */
public class QualifierAnnotationAutowireCandidateResolver extends GenericTypeAwareAutowireCandidateResolver {
    private final Set<Class<? extends Annotation>> qualifierTypes = new LinkedHashSet<>(2);

    private Class<? extends Annotation> valueAnnotationType = Value.class;

    public QualifierAnnotationAutowireCandidateResolver() {
        this.qualifierTypes.add(Qualifier.class);
    }
    @Override
    public boolean isAutowireCandidate(BeanDefinition bd, DependencyDescriptor descriptor) {
        boolean match = super.isAutowireCandidate(bd, descriptor);
        if (match) {
            // todo 检查Qualifiers
        }
        return match;
    }

    @Override
    public boolean isRequired(DependencyDescriptor descriptor) {
        if (!super.isRequired(descriptor)) {
            return false;
        }
        Autowired autowired = descriptor.getAnnotation(Autowired.class);
        return (autowired == null || autowired.required());
    }

    @Override
    public boolean hasQualifier(DependencyDescriptor descriptor) {
        for (Annotation ann : descriptor.getAnnotations()) {
            if (isQualifier(ann.annotationType())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isQualifier(Class<? extends Annotation> annotationType) {
        for (Class<? extends Annotation> qualifierType : this.qualifierTypes) {
            if (annotationType.equals(qualifierType) || annotationType.isAnnotationPresent(qualifierType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getSuggestedValue(DependencyDescriptor descriptor) {
        Object value = findValue(descriptor.getAnnotations());
        if (value == null) {
            MethodParameter methodParam = descriptor.getMethodParameter();
            if (methodParam != null) {
                value = findValue(methodParam.getMethodAnnotations());
            }
        }
        return value;
    }

    protected Object findValue(Annotation[] annotationsToSearch) {
        if (annotationsToSearch.length > 0) {   // qualifier annotations have to be local
            AnnotationAttributes attr = AnnotatedElementUtils.getMergedAnnotationAttributes(
                    AnnotatedElementUtils.forAnnotations(annotationsToSearch), this.valueAnnotationType);
            if (attr != null) {
                return extractValue(attr);
            }
        }
        return null;
    }

    protected Object extractValue(AnnotationAttributes attr) {
        Object value = attr.get(AnnotationUtils.VALUE);
        if (value == null) {
            throw new IllegalStateException("Value annotation must have a value attribute");
        }
        return value;
    }
}
