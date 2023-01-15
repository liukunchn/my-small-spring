package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.util.ClassUtils;

/**
 * @ClassName BeanNotOfRequiredTypeException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/2/23 15:10
 * @Version 1.0
 */
public class BeanNotOfRequiredTypeException extends BeansException {
    /** The name of the instance that was of the wrong type. */
    private final String beanName;

    /** The required type. */
    private final Class<?> requiredType;

    /** The offending type. */
    private final Class<?> actualType;

    public BeanNotOfRequiredTypeException(String beanName, Class<?> requiredType, Class<?> actualType) {
        super("Bean named '" + beanName + "' is expected to be of type '" + ClassUtils.getQualifiedName(requiredType) +
                "' but was actually of type '" + ClassUtils.getQualifiedName(actualType) + "'");
        this.beanName = beanName;
        this.requiredType = requiredType;
        this.actualType = actualType;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }

    public Class<?> getActualType() {
        return actualType;
    }
}
