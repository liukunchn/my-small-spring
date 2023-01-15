package com.maoyou.springframework.core.convert.converter;

import com.maoyou.springframework.core.convert.TypeDescriptor;

/**
 * @ClassName ConditionalConverter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 13:50
 * @Version 1.0
 */
public interface ConditionalConverter {
    boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType);
}
