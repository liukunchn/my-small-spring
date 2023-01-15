package com.maoyou.aop;

import com.maoyou.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @ClassName ExceptionAdvice
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 12:32
 * @Version 1.0
 */
public class ExceptionAdvice implements ThrowsAdvice {
    public void afterThrowing(Method method, Object[] args, Object target, Throwable th) {
        System.out.println(target.getClass().getName() + "." + method.getName() + "()异常");
    }
}
