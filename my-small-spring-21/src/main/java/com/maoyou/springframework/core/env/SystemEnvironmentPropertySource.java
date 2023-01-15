package com.maoyou.springframework.core.env;

import com.maoyou.springframework.util.Assert;

import java.util.Map;

/**
 * @ClassName SystemEnvironmentPropertySource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 15:19
 * @Version 1.0
 */
public class SystemEnvironmentPropertySource extends MapPropertySource {
    public SystemEnvironmentPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public boolean containsProperty(String name) {
        return (getProperty(name) != null);
    }

    @Override
    public Object getProperty(String name) {
        String actualName = resolvePropertyName(name);
        return super.getProperty(actualName);
    }

    protected String resolvePropertyName(String name) {
        Assert.notNull(name, "Property name must not be null");
        String resolvedName = checkPropertyName(name);
        if (resolvedName != null) {
            return resolvedName;
        }
        String upperCaseName = name.toUpperCase();
        if (!name.equals(upperCaseName)) {
            resolvedName = checkPropertyName(upperCaseName);
            if (resolvedName != null) {
                return resolvedName;
            }
        }
        return name;
    }

    private String checkPropertyName(String name) {
        if (source.containsKey(name)) {
            return name;
        }
        String noDotName = name.replace('.', '_');
        if (!name.equals(noDotName) && source.containsKey(noDotName)) {
            return noDotName;
        }
        String noHypName = name.replace('-', '_');
        if (!name.equals(noHypName) && source.containsKey(noDotName)) {
            return noHypName;
        }
        String noDotNoHypName = noDotName.replace('-', '_');
        if (!name.equals(noDotName) && source.containsKey(noDotNoHypName)) {
            return noDotNoHypName;
        }
        return null;
    }
}
