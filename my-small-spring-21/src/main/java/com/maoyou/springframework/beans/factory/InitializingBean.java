package com.maoyou.springframework.beans.factory;

/**
 * @ClassName InitializingBean
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/27 14:00
 * @Version 1.0
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
