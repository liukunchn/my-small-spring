package com.maoyou.springframework.core.convert.support;

import com.maoyou.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName StringToBooleanConverter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 12:29
 * @Version 1.0
 */
public class StringToBooleanConverter implements Converter<String, Boolean> {
    private static final Set<String> trueValues = new HashSet<>(8);

    private static final Set<String> falseValues = new HashSet<>(8);

    static {
        trueValues.add("true");
        trueValues.add("on");
        trueValues.add("yes");
        trueValues.add("1");

        falseValues.add("false");
        falseValues.add("off");
        falseValues.add("no");
        falseValues.add("0");
    }

    @Override
    public Boolean convert(String source) {
        String value = source.trim();
        if (value.isEmpty()) {
            return null;
        }
        value = value.toLowerCase();
        if (trueValues.contains(value)) {
            return Boolean.TRUE;
        }
        else if (falseValues.contains(value)) {
            return Boolean.FALSE;
        }
        else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }
}
