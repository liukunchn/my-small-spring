package com.maoyou.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @ClassName Advisor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 14:25
 * @Version 1.0
 */
public interface Advisor {
    Advice getAdvice();
}
