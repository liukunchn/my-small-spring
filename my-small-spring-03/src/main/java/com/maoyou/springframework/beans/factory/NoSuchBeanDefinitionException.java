package com.maoyou.springframework.beans.factory;


import com.maoyou.springframework.beans.BeansException;

/**
 * @ClassName NoSuchBeanDefinitionException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 10:54
 * @Version 1.0
 */
public class NoSuchBeanDefinitionException extends BeansException {

    private String beanName;

    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' available");
        this.beanName = name;
    }

    public String getBeanName() {
        return beanName;
    }
}
