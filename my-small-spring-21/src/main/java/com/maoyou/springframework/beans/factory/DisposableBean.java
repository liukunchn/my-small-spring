package com.maoyou.springframework.beans.factory;

/**
 * @ClassName DisposableBean
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/27 19:56
 * @Version 1.0
 */
public interface DisposableBean {
    void destroy() throws Exception;
}
