package com.maoyou.springframework.beans.factory.config;

/**
 * @ClassName BeanReference
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/17 12:32
 * @Version 1.0
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
