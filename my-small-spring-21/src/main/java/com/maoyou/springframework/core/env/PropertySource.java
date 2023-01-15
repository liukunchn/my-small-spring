package com.maoyou.springframework.core.env;

import com.maoyou.springframework.util.Assert;

import java.util.Objects;

/**
 * @ClassName PropertySource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 14:52
 * @Version 1.0
 */
public abstract class PropertySource<T> {
    protected final String name;
    protected final T source;

    public PropertySource(String name, T source) {
        Assert.hasText(name, "Property source name must contain at least one character");
        Assert.notNull(source, "Property source must not be null");
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public T getSource() {
        return source;
    }

    public abstract Object getProperty(String name);

    public boolean containsProperty(String name) {
        return getProperty(name) != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertySource<?> that = (PropertySource<?>) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
