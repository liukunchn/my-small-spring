package com.maoyou.springframework.beans.factory.config;

import com.maoyou.springframework.beans.MutablePropertyValues;
import com.maoyou.springframework.beans.PropertyValue;
import com.maoyou.springframework.util.ObjectUtils;
import com.maoyou.springframework.util.StringValueResolver;

import java.util.List;

/**
 * @ClassName BeanDefinitionVisitor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 16:49
 * @Version 1.0
 */
public class BeanDefinitionVisitor {
    private final StringValueResolver valueResolver;

    public BeanDefinitionVisitor(StringValueResolver valueResolver) {
        this.valueResolver = valueResolver;
    }

    public void visitBeanDefinition(BeanDefinition beanDefinition) {
        visitPropertyValues(beanDefinition.getPropertyValues());
    }

    private void visitPropertyValues(MutablePropertyValues pvs) {
        List<PropertyValue> pvArray = pvs.getPropertyValueList();
        for (PropertyValue pv : pvArray) {
            Object newVal = resolveValue(pv.getValue());
            if (!ObjectUtils.nullSafeEquals(newVal, pv.getValue())) {
                pvs.addPropertyValue(new PropertyValue(pv.getName(), newVal));
            }
        }
    }

    private Object resolveValue(Object value) {
        if (value instanceof String) {
            return resolveStringValue((String) value);
        }
        return value;
    }

    private Object resolveStringValue(String strVal) {
        String resolvedValue = this.valueResolver.resolveStringValue(strVal);
        return (strVal.equals(resolvedValue) ? strVal : resolvedValue);
    }
}
