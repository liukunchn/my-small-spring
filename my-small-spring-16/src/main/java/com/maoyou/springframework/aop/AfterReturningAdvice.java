package com.maoyou.springframework.aop;

import java.lang.reflect.Method;

/**
 * @ClassName AfterReturningAdvice
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 10:49
 * @Version 1.0
 */
public interface AfterReturningAdvice extends AfterAdvice {
    void afterReturning(Object returnValue, Method method, Object[] args, Object target);
}
