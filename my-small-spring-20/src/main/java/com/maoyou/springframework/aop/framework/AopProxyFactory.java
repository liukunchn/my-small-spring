package com.maoyou.springframework.aop.framework;

import com.maoyou.springframework.aop.AdvisedSupport;

/**
 * @ClassName AopProxyFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/28 14:43
 * @Version 1.0
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(AdvisedSupport config) throws RuntimeException;
}
