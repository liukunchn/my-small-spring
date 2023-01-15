package com.maoyou.springframework.core.type;

import com.maoyou.springframework.core.annotation.AnnotatedElementUtils;
import com.maoyou.springframework.core.annotation.MergedAnnotations;
import com.maoyou.springframework.core.annotation.RepeatableContainers;
import com.maoyou.springframework.util.Assert;
import com.maoyou.springframework.util.MultiValueMap;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @ClassName StandardMethodMetadata
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/18 17:05
 * @Version 1.0
 */
public class StandardMethodMetadata implements MethodMetadata {
    private final Method introspectedMethod;

    private final boolean nestedAnnotationsAsMap;

    private final MergedAnnotations mergedAnnotations;

    @Deprecated
    public StandardMethodMetadata(Method introspectedMethod) {
        this(introspectedMethod, false);
    }

    @Deprecated
    public StandardMethodMetadata(Method introspectedMethod, boolean nestedAnnotationsAsMap) {
        Assert.notNull(introspectedMethod, "Method must not be null");
        this.introspectedMethod = introspectedMethod;
        this.nestedAnnotationsAsMap = nestedAnnotationsAsMap;
        this.mergedAnnotations = MergedAnnotations.from(
                introspectedMethod, MergedAnnotations.SearchStrategy.DIRECT, RepeatableContainers.none());
    }

    @Override
    public MergedAnnotations getAnnotations() {
        return mergedAnnotations;
    }

    @Override
    public String getMethodName() {
        return introspectedMethod.getName();
    }

    @Override
    public String getDeclaringClassName() {
        return introspectedMethod.getDeclaringClass().getName();
    }

    @Override
    public String getReturnTypeName() {
        return introspectedMethod.getReturnType().getName();
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(introspectedMethod.getModifiers());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(introspectedMethod.getModifiers());
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(introspectedMethod.getModifiers());
    }

    @Override
    public boolean isOverridable() {
        return !isStatic() && !isFinal() && !isPrivate();
    }

    private boolean isPrivate() {
        return Modifier.isPrivate(introspectedMethod.getModifiers());
    }

    @Override
    public Map<String, Object> getAnnotationAttributes(String annotationName, boolean classValuesAsString) {
        if (this.nestedAnnotationsAsMap) {
            return MethodMetadata.super.getAnnotationAttributes(annotationName, classValuesAsString);
        }
        return AnnotatedElementUtils.getMergedAnnotationAttributes(this.introspectedMethod,
                annotationName, classValuesAsString, false);
    }

    @Override
    public MultiValueMap<String, Object> getAllAnnotationAttributes(String annotationName, boolean classValuesAsString) {
        if (this.nestedAnnotationsAsMap) {
            return MethodMetadata.super.getAllAnnotationAttributes(annotationName, classValuesAsString);
        }
        return AnnotatedElementUtils.getAllAnnotationAttributes(this.introspectedMethod,
                annotationName, classValuesAsString, false);
    }
}
