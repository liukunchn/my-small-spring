package com.maoyou.springframework.aop;

/**
 * @ClassName PointcutAdvisor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 14:26
 * @Version 1.0
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
