package com.maoyou.springframework.core.env;

/**
 * @ClassName PropertySourcesPropertyResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/22 14:16
 * @Version 1.0
 */
public class PropertySourcesPropertyResolver extends AbstractPropertyResolver {
    private final PropertySources propertySources;

    public PropertySourcesPropertyResolver(PropertySources propertySources) {
        this.propertySources = propertySources;
    }

    @Override
    public boolean containsProperty(String key) {
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.containsProperty(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, String.class, true);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType) {
        return getProperty(key, targetType, true);
    }

    @Override
    protected String getPropertyAsRawString(String key) {
        return getProperty(key, String.class, false);
    }

    private <T> T getProperty(String key, Class<T> targetValueType, boolean resolveNestedPlaceholders) {
        if (propertySources != null) {
            for (PropertySource<?> propertySource : propertySources) {
                Object value = propertySource.getProperty(key);
                if (value != null) {
                    if (value instanceof String && resolveNestedPlaceholders) {
                        value = resolveNestedPlaceholders((String) value);
                    }
                    return convertValueIfNecessary(value, targetValueType);
                }
            }
        }
        return null;
    }
}
