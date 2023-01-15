package com.maoyou.component;

import com.maoyou.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @ClassName Annotation1
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/11 11:23
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Annotation1 {
    @AliasFor(attribute = "test1")
    String value() default "test1";
    @AliasFor(attribute = "value")
    String test1() default "test1";
}
