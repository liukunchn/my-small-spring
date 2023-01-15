package com.maoyou.springframework.core.env;

import com.maoyou.springframework.core.convert.support.ConfigurableConversionService;

/**
 * @ClassName ConfigurablePropertyResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/22 11:17
 * @Version 1.0
 */
public interface ConfigurablePropertyResolver extends PropertyResolver {
    /**
     * 【AbstractPropertyResolver】获取转换服务
     * @return
     */
    ConfigurableConversionService getConversionService();

    /**
     * 【AbstractPropertyResolver】设置转换服务
     * @param conversionService
     */
    void setConversionService(ConfigurableConversionService conversionService);

    /**
     * 【AbstractPropertyResolver】设置忽略无法解析占位符。true原样返回，false抛出异常。getProperty及其变体的实现都需要检查这里的值
     * @param ignoreUnresolvableNestedPlaceholders
     */
    void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders);
}
