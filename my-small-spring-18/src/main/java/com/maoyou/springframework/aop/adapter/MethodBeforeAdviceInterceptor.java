package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.BeforeAdvice;
import com.maoyou.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @ClassName MethodBeforeAdviceInterceptor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 10:45
 * @Version 1.0
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {
    private final MethodBeforeAdvice advice;
    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
