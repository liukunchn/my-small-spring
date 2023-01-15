package com.maoyou.springframework.core.env;

/**
 * @ClassName PropertySources
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 16:06
 * @Version 1.0
 */
public interface PropertySources extends Iterable<PropertySource<?>> {
    boolean contains(String name);
    PropertySource<?> get(String name);
}
