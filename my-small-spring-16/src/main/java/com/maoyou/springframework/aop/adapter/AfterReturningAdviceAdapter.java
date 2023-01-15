package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.AfterReturningAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @ClassName AfterReturningAdviceAdapter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 11:56
 * @Version 1.0
 */
public class AfterReturningAdviceAdapter implements AdvisorAdapter {
    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof AfterReturningAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        return new AfterReturningAdviceInterceptor((AfterReturningAdvice) advisor.getAdvice());
    }
}
