package com.maoyou.springframework.beans;

/**
 * @ClassName BeanCreationException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/16 14:38
 * @Version 1.0
 */
public class BeanCreationException extends FatalBeanException {

    private final String beanName;

    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '" + beanName + "'" + ": " + msg);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String msg, Throwable cause) {
        this(beanName, msg);
        initCause(cause);
    }

    public String getBeanName() {
        return beanName;
    }
}
