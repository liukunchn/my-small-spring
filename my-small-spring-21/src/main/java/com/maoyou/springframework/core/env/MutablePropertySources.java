package com.maoyou.springframework.core.env;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName MutablePropertySources
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 16:12
 * @Version 1.0
 */
public class MutablePropertySources implements PropertySources {
    private final List<PropertySource<?>> propertySourceList = new CopyOnWriteArrayList<>();

    public MutablePropertySources() {
    }

    public MutablePropertySources(PropertySources propertySources) {
        for (PropertySource<?> propertySource : propertySources) {
            addLast(propertySource);
        }
    }

    public void addLast(PropertySource<?> propertySource) {
        synchronized (this.propertySourceList) {
            this.propertySourceList.remove(propertySource);
            this.propertySourceList.add(propertySource);
        }
    }

    public void addFirst(PropertySource<?> propertySource) {
        synchronized (this.propertySourceList) {
            this.propertySourceList.remove(propertySource);
            this.propertySourceList.add(0, propertySource);
        }
    }

    @Override
    public boolean contains(String name) {
        for (PropertySource<?> propertySource : propertySourceList) {
            if (propertySource.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PropertySource<?> get(String name) {
        for (PropertySource<?> propertySource : propertySourceList) {
            if (propertySource.getName().equals(name)) {
                return propertySource;
            }
        }
        return null;
    }

    @Override
    public Iterator<PropertySource<?>> iterator() {
        return this.propertySourceList.iterator();
    }
}
