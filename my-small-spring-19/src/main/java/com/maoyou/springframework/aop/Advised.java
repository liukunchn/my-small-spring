package com.maoyou.springframework.aop;

/**
 * @ClassName Advised
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 16:50
 * @Version 1.0
 */
public interface Advised extends TargetClassAware {
    boolean isProxyTargetClass();
    void setTargetSource(TargetSource targetSource);
    TargetSource getTargetSource();
    Advisor[] getAdvisors();
    void addAdvisor(Advisor advisor);
}
