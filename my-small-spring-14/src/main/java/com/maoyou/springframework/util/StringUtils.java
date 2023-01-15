package com.maoyou.springframework.util;

/**
 * @ClassName StringUtils
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 17:25
 * @Version 1.0
 */
public class StringUtils {
    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }
}
