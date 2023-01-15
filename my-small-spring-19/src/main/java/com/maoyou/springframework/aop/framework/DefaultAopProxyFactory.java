package com.maoyou.springframework.aop.framework;

import com.maoyou.springframework.aop.AdvisedSupport;

/**
 * @ClassName DefaultAopProxyFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/28 14:44
 * @Version 1.0
 */
public class DefaultAopProxyFactory implements AopProxyFactory {
    @Override
    public AopProxy createAopProxy(AdvisedSupport config) throws RuntimeException {
        if (config.isProxyTargetClass()) {
            return new CglibAopProxy(config);
        } else {
            return new JdkDynamicAopProxy(config);
        }
    }
}
