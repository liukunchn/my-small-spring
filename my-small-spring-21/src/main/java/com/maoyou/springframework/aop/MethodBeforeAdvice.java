package com.maoyou.springframework.aop;

import java.lang.reflect.Method;

/**
 * @ClassName MethodBeforeAdvice
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 10:43
 * @Version 1.0
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target);
}
