package com.maoyou.conversionservice;

import com.maoyou.springframework.core.convert.TypeDescriptor;
import com.maoyou.springframework.core.convert.converter.ConditionalGenericConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName StringToDateConverter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/13 16:26
 * @Version 1.0
 */
public class StringToDateConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return targetType.hasAnnotation(DateFormat.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Date.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        DateFormat dateFormat = targetType.getAnnotation(DateFormat.class);
        String dateFormatString = dateFormat.value();
        SimpleDateFormat format = new SimpleDateFormat(dateFormatString);
        try {
            Date parse = format.parse((String) source);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
