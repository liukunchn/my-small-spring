package com.maoyou.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Autowired
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/20 15:35
 * @Version 1.0
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;

}
