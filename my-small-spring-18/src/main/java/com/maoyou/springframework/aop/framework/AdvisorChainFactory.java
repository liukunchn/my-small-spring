package com.maoyou.springframework.aop.framework;

import com.maoyou.springframework.aop.Advised;
import com.maoyou.springframework.aop.Advisor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName AdvisorChainFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 15:29
 * @Version 1.0
 */
public interface AdvisorChainFactory {
    List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advisor[] advisors, Method method, Class<?> targetClass);
    List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advised advised, Method method, Class<?> targetClass);
}
