package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.InjectionPoint;
import com.maoyou.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @ClassName DependencyDescriptor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/19 14:20
 * @Version 1.0
 */
public class DependencyDescriptor extends InjectionPoint {
    private final Class<?> declaringClass;

    private String methodName;

    private Class<?>[] parameterTypes;

    private int parameterIndex;

    private String fieldName;

    private final boolean required;

    private final boolean eager;

    private int nestingLevel = 1;

    private Class<?> containingClass;

    public DependencyDescriptor(MethodParameter methodParameter, boolean required) {
        this(methodParameter, required, true);
    }

    public DependencyDescriptor(MethodParameter methodParameter, boolean required, boolean eager) {
        super(methodParameter);
        this.required = required;
        this.eager = eager;
        this.declaringClass = methodParameter.getDeclaringClass();
        this.methodName = methodParameter.getExecutable().getName();
        this.parameterTypes = methodParameter.getExecutable().getParameterTypes();
        this.containingClass = methodParameter.getContainingClass();
    }

    public DependencyDescriptor(Field field, boolean required) {
        this(field, required, true);
    }

    public DependencyDescriptor(Field field, boolean required, boolean eager) {
        super(field);
        this.required = required;
        this.eager = eager;
        this.declaringClass = field.getDeclaringClass();
        this.fieldName = field.getName();
    }

    /**
     * 通过filed和param获取的containingClass可能不是最终实现类？
     * @param containingClass
     */
    public void setContainingClass(Class<?> containingClass) {
        this.containingClass = containingClass;
        if (this.methodParameter != null) {
            this.methodParameter = this.methodParameter.withContainingClass(containingClass);
        }
    }

    public Class<?> getDependencyType() {
        if (this.field != null) {
            return this.field.getType();
        }
        else {
            return obtainMethodParameter().getParameterType();
        }
    }

    public boolean isRequired() {
        if (!this.required) {
            return false;
        }
        if (this.field != null) {
            return !(this.field.getType() == Optional.class || hasNullableAnnotation());
        }
        else {
            return !obtainMethodParameter().isOptional();
        }
    }

    private boolean hasNullableAnnotation() {
        for (Annotation ann : getAnnotations()) {
            if ("Nullable".equals(ann.annotationType().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEager() {
        return this.eager;
    }

    public Object resolveCandidate(String beanName, Class<?> requiredType, BeanFactory beanFactory)
            throws BeansException {
        return beanFactory.getBean(beanName);
    }
}
