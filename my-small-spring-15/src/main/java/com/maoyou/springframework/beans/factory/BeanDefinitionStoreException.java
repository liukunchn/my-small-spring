package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.FatalBeanException;

/**
 * @ClassName BeanDefinitionStoreException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 12:25
 * @Version 1.0
 */
public class BeanDefinitionStoreException extends FatalBeanException {
    public BeanDefinitionStoreException(String message) {
        super(message);
    }

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
