package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.BeansException;

/**
 * @ClassName BeanNameAware
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 13:47
 * @Version 1.0
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String beanName) throws BeansException;
}
