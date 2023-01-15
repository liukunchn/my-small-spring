package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.core.MethodParameter;
import com.maoyou.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;

/**
 * @ClassName InjectionPoint
 * @Description 注入点，可能是Filed，也可能是MethodPatameter
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/19 14:06
 * @Version 1.0
 */
public class InjectionPoint {
    protected MethodParameter methodParameter;

    protected Field field;

    private volatile Annotation[] fieldAnnotations;

    public InjectionPoint(MethodParameter methodParameter) {
        Assert.notNull(methodParameter, "MethodParameter must not be null");
        this.methodParameter = methodParameter;
    }

    public InjectionPoint(Field field) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
    }

    public MethodParameter getMethodParameter() {
        return methodParameter;
    }

    public Field getField() {
        return field;
    }

    protected final MethodParameter obtainMethodParameter() {
        Assert.state(this.methodParameter != null, "Neither Field nor MethodParameter");
        return this.methodParameter;
    }

    public Annotation[] getAnnotations() {
        if (this.field != null) {
            Annotation[] fieldAnnotations = this.fieldAnnotations;
            if (fieldAnnotations == null) {
                fieldAnnotations = this.field.getAnnotations();
                this.fieldAnnotations = fieldAnnotations;
            }
            return fieldAnnotations;
        }
        else {
            return obtainMethodParameter().getParameterAnnotations();
        }
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return (this.field != null ? this.field.getAnnotation(annotationType) :
                obtainMethodParameter().getParameterAnnotation(annotationType));
    }
    public Class<?> getDeclaredType() {
        return (this.field != null ? this.field.getType() : obtainMethodParameter().getParameterType());
    }

    public Member getMember() {
        return (this.field != null ? this.field : obtainMethodParameter().getMember());
    }

    public AnnotatedElement getAnnotatedElement() {
        return (this.field != null ? this.field : obtainMethodParameter().getAnnotatedElement());
    }

}
