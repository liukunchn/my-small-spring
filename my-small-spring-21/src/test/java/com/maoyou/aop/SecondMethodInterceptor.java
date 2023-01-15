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
public class SecondMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()开始");
        try {
            Object retVal = mi.proceed();
            System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()结束");
            return retVal;
        } catch (Exception e) {
            System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()异常");
            throw e;
        }
        finally {
            System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()FINAL");
        }
    }
}
