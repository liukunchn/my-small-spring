package com.maoyou.springframework.core.env;

import com.maoyou.springframework.util.ObjectUtils;

/**
 * @ClassName EnumerablePropertySource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 15:01
 * @Version 1.0
 */
public abstract class EnumerablePropertySource<T> extends PropertySource<T> {
    public EnumerablePropertySource(String name, T source) {
        super(name, source);
    }

    public abstract String[] getPropertyNames();

    @Override
    public boolean containsProperty(String name) {
        return ObjectUtils.containsElement(getPropertyNames(), name);
    }
}
