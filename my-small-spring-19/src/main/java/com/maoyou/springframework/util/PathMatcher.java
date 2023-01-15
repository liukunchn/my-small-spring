package com.maoyou.springframework.util;

import java.util.Comparator;
import java.util.Map;

/**
 * @ClassName PathMatcher
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/3 15:16
 * @Version 1.0
 */
public interface PathMatcher {

    boolean isPattern(String path);

    boolean match(String pattern, String path);

    boolean matchStart(String pattern, String path);

    String extractPathWithinPattern(String pattern, String path);

    Map<String, String> extractUriTemplateVariables(String pattern, String path);

    Comparator<String> getPatternComparator(String path);

    String combine(String pattern1, String pattern2);
}
