package com.maoyou.springframework.aop.framework;

import com.maoyou.springframework.aop.Advised;
import com.maoyou.springframework.aop.AdvisedSupport;
import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.TargetSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName ProxyFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 16:57
 * @Version 1.0
 */
public class ProxyFactory extends AdvisedSupport {
    private AopProxyFactory aopProxyFactory;
    public ProxyFactory() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(this);
    }

    private AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }
}
