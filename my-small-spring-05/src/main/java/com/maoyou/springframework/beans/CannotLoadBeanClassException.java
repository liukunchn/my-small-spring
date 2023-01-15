package com.maoyou.springframework.beans;

/**
 * @ClassName CannotLoadBeanClassException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 15:42
 * @Version 1.0
 */
public class CannotLoadBeanClassException extends FatalBeanException {

    private final String beanName;

    private final String beanClassName;

    public CannotLoadBeanClassException(String beanName, String beanClassName, ClassNotFoundException cause) {
        super("Cannot find class [" + beanClassName + "] for bean with name '" + beanName + "'", cause);
        this.beanName = beanName;
        this.beanClassName = beanClassName;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }
}
