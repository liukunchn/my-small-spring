package com.maoyou.springframework.core.env;

/**
 * @ClassName StandardEnvironment
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/22 23:48
 * @Version 1.0
 */
public class StandardEnvironment extends AbstractEnvironment {
    public static final String SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME = "systemEnvironment";
    public static final String SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME = "systemProperties";

    public StandardEnvironment() {
    }

    public StandardEnvironment(MutablePropertySources propertySources) {
        super(propertySources);
    }

    @Override
    protected void customizePropertySources(MutablePropertySources propertySources) {
        propertySources.addLast(new PropertiesPropertySource(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, getSystemEnvironment()));
    }
}
