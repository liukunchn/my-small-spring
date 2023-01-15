package com.maoyou.springframework.beans.factory;

import com.maoyou.springframework.beans.BeanCreationException;
import com.maoyou.springframework.beans.BeanInstantiationException;
import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.CannotLoadBeanClassException;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName BeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 10:48
 * @Version 1.0
 */
public class BeanFactory {

    private Map<String, BeanDefinition> beanDifinitionMap = new ConcurrentHashMap<>();

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDifinitionMap.put(beanName, beanDefinition);
    }

    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = beanDifinitionMap.get(beanName);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(beanName);
        }
        return bd;
    }

    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    public <T> T getBean(String name, Class<T> requriedType) throws BeansException {
        return doGetBean(name, requriedType);
    }

    public  <T> T doGetBean(String name, Class<T> requriedType) {

        BeanDefinition bd = getBeanDefinition(name);
        return (T) createBean(name, bd);
    }

    public Object createBean(String beanName, BeanDefinition bd) throws BeanCreationException {
        try {
            Object beanInstance = doCreateBean(beanName, bd);
            return beanInstance;
        } catch (BeanCreationException e) {
            throw e;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Unexpected exception during bean creation", ex);
        }
    }

    public Object doCreateBean(String beanName, BeanDefinition bd) throws BeanCreationException {
        Object bean = createBeanInstance(beanName, bd);
        return bean;
    }

    public Object createBeanInstance(String beanName, BeanDefinition bd) {
        // resolveBeanClass()
        Class<?> beanClass = resolveBeanClass(bd, beanName);
        if (beanClass != null && !Modifier.isPublic(beanClass.getModifiers())) {
            throw new BeanCreationException(beanName,
                    "Bean class isn't public, and non-public access not allowed: " + beanClass.getName());
        }
        // instantiateBean()
        return instantiateBean(beanName, bd);
    }

    public Class<?> resolveBeanClass(BeanDefinition bd, String beanName) throws CannotLoadBeanClassException {
        Class<?> clazz;
        try {
            clazz = bd.resolveBeanClass(null);
        } catch (ClassNotFoundException e) {
            throw new CannotLoadBeanClassException(beanName, bd.getBeanClassName(), e);
        }
        return clazz;
    }

    public Object instantiateBean(String beanName, BeanDefinition bd) {
        Class<?> clazz = bd.getBeanClass();
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "Specified class is an interface");
        }
        Constructor<?> constructorToUse;
        try {
            constructorToUse = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new BeanInstantiationException(clazz, "No default constructor found", e);
        }
        Object o;
        try {
            o = constructorToUse.newInstance();
        } catch (InstantiationException e) {
            throw new BeanInstantiationException(clazz, "Is it an abstract class?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(clazz, "Is the constructor accessible?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException(clazz, "Constructor threw exception", e);
        }
        // 如果创建了bean对象，把它添加到单例容器
        return o;
    }

}
