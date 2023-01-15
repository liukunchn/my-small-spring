package com.maoyou.springframework.beans;

/**
 * @ClassName FatalBeanException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 15:40
 * @Version 1.0
 */
public class FatalBeanException extends BeansException {
    public FatalBeanException(String message) {
        super(message);
    }

    public FatalBeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
