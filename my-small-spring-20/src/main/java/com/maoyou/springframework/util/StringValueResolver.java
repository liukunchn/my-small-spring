package com.maoyou.springframework.util;

/**
 * @ClassName StringValueResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 16:48
 * @Version 1.0
 */
@FunctionalInterface
public interface StringValueResolver {
    String resolveStringValue(String strVal);
}
