package com.maoyou;

import com.maoyou.springframework.beans.factory.DisposableBean;
import com.maoyou.springframework.beans.factory.InitializingBean;

/**
 * @ClassName User
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 14:01
 * @Version 1.0
 */
public class User implements InitializingBean, DisposableBean {
    private String name;
    private Org org;

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
}
