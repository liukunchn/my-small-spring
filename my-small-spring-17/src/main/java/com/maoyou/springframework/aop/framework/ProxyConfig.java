package com.maoyou.springframework.aop.framework;

/**
 * @ClassName ProxyConfig
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 15:59
 * @Version 1.0
 */
public class ProxyConfig {
    private boolean proxyTargetClass = false;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public void copyFrom(ProxyConfig proxyConfig) {
        this.proxyTargetClass = proxyConfig.proxyTargetClass;
    }
}
