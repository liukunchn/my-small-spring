package com.maoyou.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @ClassName Component
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/3 10:46
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
