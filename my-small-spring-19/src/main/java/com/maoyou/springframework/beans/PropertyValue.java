package com.maoyou.springframework.beans;

import java.util.Objects;

/**
 * @ClassName PropertyValue
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/17 12:17
 * @Version 1.0
 */
public class PropertyValue {
    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyValue that = (PropertyValue) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
