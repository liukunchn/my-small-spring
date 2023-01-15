package com.maoyou.springframework.aop;

/**
 * @ClassName TargetSource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 16:49
 * @Version 1.0
 */
public interface TargetSource extends TargetClassAware {
    Object getTarget() throws Exception;
}
