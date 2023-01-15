package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.DisposableBean;
import com.maoyou.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName DefaultSingletonBeanRegistry
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 17:34
 * @Version 1.0
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonObjects = new HashMap<>();

    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        this.disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        String[] disposableBeanNames = this.disposableBeans.keySet().toArray(new String[0]);
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            this.singletonObjects.remove(disposableBeanNames[i]);
            DisposableBean disposableBean = (DisposableBean) this.disposableBeans.remove(disposableBeanNames[i]);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                System.out.println("Destruction of bean with name '" + disposableBeanNames[i] + "' threw an exception");
                e.printStackTrace();
            }
        }
    }
}
