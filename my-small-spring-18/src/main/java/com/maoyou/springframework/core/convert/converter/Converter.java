package com.maoyou.springframework.core.convert.converter;

import com.maoyou.springframework.lang.Nullable;

/**
 * @ClassName Converter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 12:13
 * @Version 1.0
 */
@FunctionalInterface
public interface Converter<S, T> {
    @Nullable
    T convert(S source);
}
