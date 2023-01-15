package com.maoyou.springframework.core.convert.support;

import com.maoyou.springframework.core.convert.converter.Converter;
import com.maoyou.springframework.core.io.DefaultResourceLoader;
import com.maoyou.springframework.core.io.Resource;

/**
 * @ClassName StringToResourceConverter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 18:43
 * @Version 1.0
 */
public class StringToResourceConverter implements Converter<String, Resource> {
    @Override
    public Resource convert(String source) {
        String value = source.trim();
        if (value.isEmpty()) {
            return null;
        }
        return new DefaultResourceLoader().getResource(source);
    }
}
