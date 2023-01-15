package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.DisposableBean;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;

/**
 * @ClassName DisposableBeanAdapter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/27 19:59
 * @Version 1.0
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;

    private final String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition bd) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = bd.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (destroyMethodName != null) {
            bean.getClass().getDeclaredMethod(destroyMethodName).invoke(bean);
        }
    }
}
