package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.Advisor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @ClassName AdvisorAdapter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 11:53
 * @Version 1.0
 */
public interface AdvisorAdapter {
    boolean supportsAdvice(Advice advice);
    MethodInterceptor getInterceptor(Advisor advisor);
}
