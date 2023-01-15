package com.maoyou.springframework.beans;

import java.io.IOException;

/**
 * @ClassName BeanInstantiationException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 11:40
 * @Version 1.0
 */
public class BeanInstantiationException extends FatalBeanException {
    private Class<?> beanClass;

    public BeanInstantiationException(Class<?> beanClass, String msg) {
        this(beanClass, msg, null);
    }

    public BeanInstantiationException(Class<?> beanClass, String msg, Throwable cause) {
        super("Failed to instantiate [" + beanClass.getName() + "]: " + msg, cause);
        this.beanClass = beanClass;
    }

    public BeanInstantiationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

}
