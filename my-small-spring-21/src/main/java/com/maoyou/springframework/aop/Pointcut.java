package com.maoyou.springframework.aop;

/**
 * @ClassName Pointcut
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 17:24
 * @Version 1.0
 */
public interface Pointcut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
