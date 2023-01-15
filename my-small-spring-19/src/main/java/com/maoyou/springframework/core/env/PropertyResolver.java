package com.maoyou.springframework.core.env;

/**
 * @ClassName PropertyResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 17:01
 * @Version 1.0
 */
public interface PropertyResolver {
    /**
     * 【PropertySourcePropertyResolver】是否包含属性
     * @param key
     * @return
     */
    boolean containsProperty(String key);

    /**
     * 【PropertySourcePropertyResolver】获取属性,并转化为String类型
     * @param key
     * @return
     */
    String getProperty(String key);

    /**
     * 【PropertySourcePropertyResolver】获取属性，并转化为指定类型
     * @param key
     * @param targetType
     * @param <T>
     * @return
     */
    <T> T getProperty(String key, Class<T> targetType);

    /**
     * 【AbstractPropertyResolver】获取属性，并转化为String类型，如果获取不到则抛出异常
     * @param key
     * @return
     * @throws IllegalStateException
     */
    String getRequiredProperty(String key) throws IllegalStateException;

    /**
     * 【AbstractPropertyResolver】获取属性，并转化为指定类型，如果获取不到则抛出异常
     * @param key
     * @param targetType
     * @param <T>
     * @return
     * @throws IllegalStateException
     */
    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

    /**
     * 【AbstractPropertyResolver】利用getProperty解析占位符，如果无法解析则原样返回
     * @param text
     * @return
     */
    String resolvePlaceholders(String text);

    /**
     * 【AbstractPropertyResolver】利用getProperty解析占位符，如果无法解析则抛出异常
     * @param text
     * @return
     * @throws IllegalArgumentException
     */
    String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;
}
