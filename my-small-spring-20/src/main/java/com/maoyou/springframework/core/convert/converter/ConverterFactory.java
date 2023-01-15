package com.maoyou.springframework.core.convert.converter;

/**
 * @ClassName ConverterFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 12:14
 * @Version 1.0
 */
public interface ConverterFactory<S, R> {
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
