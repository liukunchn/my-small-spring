package com.maoyou.springframework.context.annotation;

import com.maoyou.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @ClassName ScopeMetadata
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/21 23:43
 * @Version 1.0
 */
public class ScopeMetadata {
    private String scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON;

    private ScopedProxyMode scopedProxyMode = ScopedProxyMode.NO;

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public ScopedProxyMode getScopedProxyMode() {
        return scopedProxyMode;
    }

    public void setScopedProxyMode(ScopedProxyMode scopedProxyMode) {
        this.scopedProxyMode = scopedProxyMode;
    }
}
