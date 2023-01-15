package com.maoyou.springframework.aop.support;

import com.maoyou.springframework.aop.Pointcut;
import com.maoyou.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @ClassName DefaultBeanFactoryPointcutAdvisor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 14:29
 * @Version 1.0
 */
public class DefaultBeanFactoryPointcutAdvisor implements PointcutAdvisor {
    private transient volatile Advice advice;
    private Pointcut pointcut;

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }
    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
    @Override
    public Advice getAdvice() {
        return advice;
    }
}
