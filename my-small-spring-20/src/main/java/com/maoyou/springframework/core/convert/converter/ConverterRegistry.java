package com.maoyou.springframework.core.convert.converter;

/**
 * @ClassName ConverterRegistry
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 16:16
 * @Version 1.0
 */
public interface ConverterRegistry {
    void addConverter(Converter<?, ?> converter);
    void addConverterFactory(ConverterFactory<?, ?> factory);
    void addConverter(GenericConverter converter);
    <S, T> void addConverter(Class<S> sourceType, Class<T> targetType, Converter<? super S, ? extends T> converter);
    void removeConvertible(Class<?> sourceType, Class<?> targetType);
}
