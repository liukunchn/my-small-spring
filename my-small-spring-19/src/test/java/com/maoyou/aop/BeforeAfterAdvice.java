package com.maoyou.aop;

import com.maoyou.springframework.aop.AfterReturningAdvice;
import com.maoyou.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @ClassName BeforeAfterAdvice
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 12:30
 * @Version 1.0
 */
public class BeforeAfterAdvice implements MethodBeforeAdvice, AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
        System.out.println(target.getClass().getName() + "." + method.getName() + "()结束");
    }

    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println(target.getClass().getName() + "." + method.getName() + "()开始");
    }
}
