package com.maoyou.springframework.core.env;

import java.util.Map;
import java.util.Properties;

/**
 * @ClassName PropertiesPropertySource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 15:12
 * @Version 1.0
 */
public class PropertiesPropertySource extends MapPropertySource {
    public PropertiesPropertySource(String name, Properties source) {
        super(name, (Map) source);
    }

    public PropertiesPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        synchronized (source) {
            return super.getPropertyNames();
        }
    }
}
