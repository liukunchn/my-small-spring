package com.maoyou.springframework.core.io;

/**
 * @ClassName ResourceLoader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 18:04
 * @Version 1.0
 */
public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX ="classpath:";
    Resource getResource(String location);
    ClassLoader getClassLoader();
}
