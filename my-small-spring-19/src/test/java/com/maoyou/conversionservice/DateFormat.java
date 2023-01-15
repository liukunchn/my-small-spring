package com.maoyou.conversionservice;

import java.lang.annotation.*;

/**
 * @ClassName DateFormat
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/12 21:37
 * @Version 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    String value() default "yyyy-MM-dd";
}
