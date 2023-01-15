package com.maoyou.springframework.beans;

import java.util.List;

/**
 * @ClassName PropertyValues
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/13 16:34
 * @Version 1.0
 */
public interface PropertyValues {
    PropertyValues addPropertyValue(PropertyValue pv);
    List<PropertyValue> getPropertyValueList();
}
