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
public class MutablePropertyValues implements PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    @Override
    public PropertyValues addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPv = this.propertyValueList.get(i);
            if (currentPv.getName().equals(pv.getName())) {
                this.propertyValueList.set(i, pv);
                return this;
            }
        }
        this.propertyValueList.add(pv);
        return this;
    }

    @Override
    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }
}
