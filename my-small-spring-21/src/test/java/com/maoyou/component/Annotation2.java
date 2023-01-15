package com.maoyou.component;

import com.maoyou.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @ClassName Annotation2
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/11 11:24
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Inherited
@Annotation1
public @interface Annotation2 {
    String test2() default "test2";
    @AliasFor(annotation = Annotation1.class, attribute = "test1")
    String test() default "test";
}
