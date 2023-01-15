package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.AfterAdvice;
import com.maoyou.springframework.aop.AfterReturningAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @ClassName AfterReturningAdviceInterceptor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 10:51
 * @Version 1.0
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {
    private final AfterReturningAdvice advice;
    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object returnValue = mi.proceed();
        advice.afterReturning(returnValue, mi.getMethod(), mi.getArguments(), mi.getThis());
        return returnValue;
    }
}
