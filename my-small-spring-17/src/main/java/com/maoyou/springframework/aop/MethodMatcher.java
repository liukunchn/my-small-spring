package com.maoyou.springframework.aop;

import java.lang.reflect.Method;

/**
 * @ClassName MethodMatcher
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 17:24
 * @Version 1.0
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> clazz);
}
