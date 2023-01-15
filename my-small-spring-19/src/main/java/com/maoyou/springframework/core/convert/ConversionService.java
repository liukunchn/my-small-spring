package com.maoyou.springframework.core.convert;

import com.maoyou.springframework.lang.Nullable;

/**
 * @ClassName ConversionService
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 16:14
 * @Version 1.0
 */
public interface ConversionService {
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);
    boolean canConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType);
    <T> T convert(@Nullable Object source, Class<T> targetType);
    Object convert(@Nullable Object source, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType);
}
