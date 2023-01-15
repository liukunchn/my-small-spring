package com.maoyou.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @ClassName FirstMethodInterceptor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/29 11:26
 * @Version 1.0
 */
public class FirstMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = mi.proceed();
        long end = System.currentTimeMillis();
        System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()耗时：" + (end - start) + "毫秒");
        return retVal;
    }
}
