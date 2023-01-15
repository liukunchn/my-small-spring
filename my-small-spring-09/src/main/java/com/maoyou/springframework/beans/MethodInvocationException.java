package com.maoyou.springframework.beans;

/**
 * @ClassName MethodInvocationException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/17 16:47
 * @Version 1.0
 */
public class MethodInvocationException extends BeansException {
    public MethodInvocationException(String message) {
        super(message);
    }

    public MethodInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
