package com.maoyou.springframework.aop;

/**
 * @ClassName ClassFilter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 17:23
 * @Version 1.0
 */
public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
