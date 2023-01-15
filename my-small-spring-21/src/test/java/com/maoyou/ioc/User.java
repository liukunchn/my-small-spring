package com.maoyou.ioc;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.BeanFactoryAware;
import com.maoyou.springframework.beans.factory.DisposableBean;
import com.maoyou.springframework.beans.factory.InitializingBean;
import com.maoyou.springframework.context.ApplicationContext;
import com.maoyou.springframework.context.ApplicationContextAware;
import com.maoyou.springframework.context.EnvironmentAware;
import com.maoyou.springframework.core.env.Environment;

/**
 * @ClassName User
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 14:01
 * @Version 1.0
 */
public class User implements InitializingBean, DisposableBean, BeanFactoryAware, ApplicationContextAware, EnvironmentAware {
    private String name;
    private Org org;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private Environment environment;

    public User() {
    }

    public User(String name, Org org) {
        this.name = name;
        this.org = org;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", org=" + org +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行了afterPropertiesSet初始化方法：" + this.name);
    }

    public void init() {
        System.out.println("执行了init初始化方法：" + this.name);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行了destroy销毁方法" + this.name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
