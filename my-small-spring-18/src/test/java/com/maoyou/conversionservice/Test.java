package com.maoyou.conversionservice;

import com.maoyou.springframework.context.support.ClassPathXmlApplicationContext;
import com.maoyou.springframework.core.convert.ConversionService;
import com.maoyou.springframework.core.convert.TypeDescriptor;
import com.maoyou.springframework.core.convert.converter.ConditionalGenericConverter;
import com.maoyou.springframework.core.convert.converter.Converter;
import com.maoyou.springframework.core.convert.support.DefaultConversionService;
import com.maoyou.springframework.core.convert.support.StringToNumberConverterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/11 22:57
 * @Version 1.0
 */
public class Test {
    private String dateStr;

    @DateFormat
    private Date date;

    Set<?> sets;
    Set<String> strs;

    @org.junit.Test
    public void testConverter() {
        StringToNumberConverterFactory factory = new StringToNumberConverterFactory();
        Converter<String, Integer> converter = factory.getConverter(Integer.class);
        Integer integer = converter.convert("100");
        System.out.println(integer);
    }

    @org.junit.Test
    public void testConditionalGenericConverter() throws NoSuchFieldException {
        ConditionalGenericConverter converter = new StringToDateConverter();
        if (converter.matches(new TypeDescriptor(Test.class.getDeclaredField("dateStr")), new TypeDescriptor(Test.class.getDeclaredField("date")))) {
            Object convert = converter.convert("2021-12-12 21:44:00", new TypeDescriptor(Test.class.getDeclaredField("dateStr")), new TypeDescriptor(Test.class.getDeclaredField("date")));
            System.out.println(convert);
        }
    }

    @org.junit.Test
    public void testDefaultConversionService() throws NoSuchFieldException {
        DefaultConversionService conversionService = new DefaultConversionService();
        Boolean bool = conversionService.convert("true", Boolean.class);
        System.out.println(bool);
        ConditionalGenericConverter converter = new StringToDateConverter();
        conversionService.addConverter(converter);
        Object obj = conversionService.convert("2021-12-12 21:44:00", new TypeDescriptor(Test.class.getDeclaredField("dateStr")), new TypeDescriptor(Test.class.getDeclaredField("date")));
        System.out.println(obj);
        Set<String> set = new HashSet<>();
        set.add("123");
        set.add("456");
        Object convert = conversionService.convert(set, new TypeDescriptor(Test.class.getDeclaredField("strs")), new TypeDescriptor(Test.class.getDeclaredField("sets")));
        System.out.println(convert);
    }

    @org.junit.Test
    public void testSpringConversion () {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-conversion.xml");
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println(dataSource);
    }
}
