package com.maoyou.springframework.core.env;

import java.util.Map;

/**
 * @ClassName MapPropertySource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 15:05
 * @Version 1.0
 */
public class MapPropertySource extends EnumerablePropertySource<Map<String, Object>> {

    public MapPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return source.keySet().toArray(new String[0]);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }

    @Override
    public boolean containsProperty(String name) {
        return source.containsKey(name);
    }
}
