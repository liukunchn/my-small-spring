package com.maoyou.springframework.core.env;

import java.util.Map;

/**
 * @ClassName ConfigurableEnvironment
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/22 17:07
 * @Version 1.0
 */
public interface ConfigurableEnvironment extends Environment, ConfigurablePropertyResolver {
    void setActiveProfiles(String... profiles);
    void addActiveProfile(String profile);
    void setDefaultProfiles(String... profiles);
    MutablePropertySources getPropertySources();
    Map<String, Object> getSystemProperties();
    Map<String, Object> getSystemEnvironment();
    void merge(ConfigurableEnvironment parent);
}
