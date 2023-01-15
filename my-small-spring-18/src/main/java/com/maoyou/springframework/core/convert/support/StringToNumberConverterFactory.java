package com.maoyou.springframework.core.convert.support;

import com.maoyou.springframework.core.convert.converter.Converter;
import com.maoyou.springframework.core.convert.converter.ConverterFactory;
import com.maoyou.springframework.util.NumberUtils;

/**
 * @ClassName StringToNumberConverterFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 12:17
 * @Version 1.0
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber(targetType);
    }

    private static final class StringToNumber<T extends Number> implements Converter<String, T> {
        private final Class<T> targetType;

        private StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            return NumberUtils.parseNumber(source, this.targetType);
        }
    }
}
