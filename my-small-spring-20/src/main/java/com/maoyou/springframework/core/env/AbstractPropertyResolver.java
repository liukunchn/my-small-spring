package com.maoyou.springframework.core.env;

import com.maoyou.springframework.core.convert.support.ConfigurableConversionService;
import com.maoyou.springframework.core.convert.support.DefaultConversionService;
import com.maoyou.springframework.util.Assert;
import com.maoyou.springframework.util.PropertyPlaceholderHelper;

/**
 * @ClassName AbstractPropertyResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/22 11:33
 * @Version 1.0
 */
public abstract class AbstractPropertyResolver implements ConfigurablePropertyResolver {
    private volatile ConfigurableConversionService conversionService;
    private boolean ignoreUnresolvableNestedPlaceholders = false;
    private String placeholderPrefix = "${";
    private String placeholderSuffix = "}";
    private String valueSeparator = ":";
    private PropertyPlaceholderHelper nonStrictHelper;
    private PropertyPlaceholderHelper strictHelper;
    @Override
    public ConfigurableConversionService getConversionService() {
        ConfigurableConversionService cs = this.conversionService;
        if (cs == null) {
            synchronized (this) {
                cs = this.conversionService;
                if (cs == null) {
                    cs = new DefaultConversionService();
                    this.conversionService = cs;
                }
            }
        }
        return cs;
    }

    @Override
    public void setConversionService(ConfigurableConversionService conversionService) {
        Assert.notNull(conversionService, "ConversionService must not be null");
        this.conversionService = conversionService;
    }

    @Override
    public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {
        this.ignoreUnresolvableNestedPlaceholders = ignoreUnresolvableNestedPlaceholders;
    }


    @Override
    public boolean containsProperty(String key) {
        return getProperty(key) != null;
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, String.class);
    }

    @Override
    public String getRequiredProperty(String key) throws IllegalStateException {
        String value = getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Requried key '" + key + "' not found");
        }
        return value;
    }

    @Override
    public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        T value = getProperty(key, targetType);
        if (value == null) {
            throw new IllegalStateException("Requried key '" + key + "' not found");
        }
        return value;
    }

    @Override
    public String resolvePlaceholders(String text) {
        if (nonStrictHelper == null) {
            nonStrictHelper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix, valueSeparator, true);
        }
        return nonStrictHelper.replacePlaceholders(text, this::getPropertyAsRawString);
    }

    @Override
    public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        if (strictHelper == null) {
            strictHelper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix, valueSeparator, false);
        }
        return strictHelper.replacePlaceholders(text, this::getPropertyAsRawString);
    }

    protected abstract String getPropertyAsRawString(String key);

    /**
     * 解析属性的值中的占位符，getProperty方法会调用此方法，根据ignore参数调用resolvePlaceholders或者resolveRequiredPlaceholders
     * @param value
     * @return
     */
    protected String resolveNestedPlaceholders(String value) {
        if (value.isEmpty()) {
            return value;
        }
        return ignoreUnresolvableNestedPlaceholders ? resolvePlaceholders(value) : resolveRequiredPlaceholders(value);
    }

    /**
     * 如果必要则转换类型，getProperty方法会在解析占位符之后调用此方法
     * @param value
     * @param targetType
     * @param <T>
     * @return
     */
    protected <T> T convertValueIfNecessary(Object value, Class<T> targetType) {
        return getConversionService().convert(value, targetType);
    }
}
