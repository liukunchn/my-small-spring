package com.maoyou.springframework.core;

/**
 * @ClassName NestedRuntimeException
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 14:26
 * @Version 1.0
 */
public abstract class NestedRuntimeException extends RuntimeException {
    public NestedRuntimeException(String message) {
        super(message);
    }

    public NestedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return buildMessage(super.getMessage(), getCause());
    }

    public static String buildMessage(String message, Throwable cause) {
        if (cause == null) {
            return message;
        }
        StringBuilder sb = new StringBuilder(64);
        if (message != null) {
            sb.append(message).append("; ");
        }
        sb.append("nested exception is ").append(cause);
        return sb.toString();
    }
}
