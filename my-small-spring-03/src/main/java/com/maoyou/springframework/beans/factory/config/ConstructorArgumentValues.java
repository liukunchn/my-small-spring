package com.maoyou.springframework.beans.factory.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConstructorArgumentValues
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/16 18:36
 * @Version 1.0
 */
public class ConstructorArgumentValues {

    private final List<ValueHolder> genericArgumentValues = new ArrayList<>();

    public void addValueHolder(ValueHolder valueHolder) {
        genericArgumentValues.add(valueHolder);
    }

    public List<ValueHolder> getGenericArgumentValues() {
        return genericArgumentValues;
    }

    public boolean isEmpty() {
        return (this.genericArgumentValues.isEmpty());
    }

    public static class ValueHolder {

        private String name;

        private Object value;

        public ValueHolder(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
