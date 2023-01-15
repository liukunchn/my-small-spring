package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @ClassName MethodBeforeAdviceAdapter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 11:53
 * @Version 1.0
 */
public class MethodBeforeAdviceAdapter implements AdvisorAdapter {
    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof MethodBeforeAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        return new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advisor.getAdvice());
    }
}
