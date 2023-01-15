package com.maoyou.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Value
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/20 15:36
 * @Version 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * The actual value expression such as <code>#{systemProperties.myProp}</code>
     * or property placeholder such as <code>${my.app.myProp}</code>.
     */
    String value();

}
