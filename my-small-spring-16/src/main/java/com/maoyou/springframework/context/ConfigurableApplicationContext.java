package com.maoyou.springframework.context;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @ClassName ConfigurableApplicationContext
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 17:44
 * @Version 1.0
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    void refresh() throws BeansException, IllegalStateException;

    void registerShutdownHook();

    void close();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
