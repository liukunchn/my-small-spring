package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.ThrowsAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @ClassName ThrowsAdviceAdapter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 12:01
 * @Version 1.0
 */
public class ThrowsAdviceAdapter implements AdvisorAdapter {
    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof ThrowsAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        return new ThrowsAdviceInterceptor(advisor.getAdvice());
    }
}
