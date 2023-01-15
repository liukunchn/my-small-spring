package com.maoyou.springframework.beans;

import com.maoyou.springframework.core.NestedRuntimeException;

/**
 * @ClassName BeansException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 10:59
 * @Version 1.0
 */
public abstract class BeansException extends NestedRuntimeException {
    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
