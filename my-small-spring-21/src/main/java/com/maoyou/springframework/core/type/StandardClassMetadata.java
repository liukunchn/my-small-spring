package com.maoyou.springframework.core.type;

import com.maoyou.springframework.util.Assert;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @ClassName StandardClassMetadata
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/18 11:38
 * @Version 1.0
 */
public class StandardClassMetadata implements ClassMetadata {
    private final Class<?> introspectedClass;

    public StandardClassMetadata(Class<?> introspectedClass) {
        Assert.notNull(introspectedClass, "Class must not be null");
        this.introspectedClass = introspectedClass;
    }

    public final Class<?> getIntrospectedClass() {
        return this.introspectedClass;
    }

    @Override
    public String getClassName() {
        return introspectedClass.getName();
    }

    @Override
    public boolean isInterface() {
        return introspectedClass.isInterface();
    }

    @Override
    public boolean isAnnotation() {
        return introspectedClass.isAnnotation();
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(introspectedClass.getModifiers());
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(introspectedClass.getModifiers());
    }

    @Override
    public boolean isIndependent() {
        return (!hasEnclosingClass() ||
                (this.introspectedClass.getDeclaringClass() != null &&
                        Modifier.isStatic(this.introspectedClass.getModifiers())));
    }

    @Override
    public String getEnclosingClassName() {
        Class<?> enclosingClass = introspectedClass.getEnclosingClass();
        return enclosingClass == null ? null : enclosingClass.getName();
    }

    @Override
    public String getSuperClassName() {
        Class<?> superclass = introspectedClass.getSuperclass();
        return superclass == null ? null : superclass.getName();
    }

    @Override
    public String[] getInterfaceNames() {
        return Arrays.stream(introspectedClass.getInterfaces()).map(Class::getName).toArray(String[]::new);
    }

    @Override
    public String[] getMemberClassNames() {
        return Arrays.stream(introspectedClass.getDeclaredClasses()).map(Class::getName).toArray(String[]::new);
    }
}
