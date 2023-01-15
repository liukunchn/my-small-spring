package com.maoyou.springframework.aop;

/**
 * @ClassName TargetClassAware
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 16:48
 * @Version 1.0
 */
public interface TargetClassAware {
    Class<?> getTargetClass();
}
