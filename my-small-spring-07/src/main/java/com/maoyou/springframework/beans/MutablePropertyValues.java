package com.maoyou.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MutablePropertyValues
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/17 12:16
 * @Version 1.0
 */
public class MutablePropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        propertyValueList.add(pv);
        return this;
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }
}
