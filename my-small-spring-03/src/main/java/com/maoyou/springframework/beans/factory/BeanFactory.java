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
import java.util.stream.Collectors;

/**
 * @ClassName BeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 10:48
 * @Version 1.0
 */
public class BeanFactory {

    private Map<String, BeanDefinition> beanDifinitionMap = new ConcurrentHashMap<>();

    private Map<String, Object> singletonObjects = new HashMap<>();

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
        return doGetBean(name, null, null);
    }

    public <T> T getBean(String name, Class<T> requriedType) throws BeansException {
        return doGetBean(name, requriedType, null);
    }

    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, null, args);
    }

    public  <T> T doGetBean(String name, Class<T> requriedType, Object... args) throws BeansException {
        // 如果单例容器中有，从单例容器中获取
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }
        BeanDefinition bd = getBeanDefinition(name);
        return (T) createBean(name, bd, args);
    }

    public Object createBean(String beanName, BeanDefinition bd, Object... args) throws BeanCreationException {
        try {
            Object beanInstance = doCreateBean(beanName, bd, args);
            return beanInstance;
        } catch (BeanCreationException e) {
            throw e;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Unexpected exception during bean creation", ex);
        }
    }

    public Object doCreateBean(String beanName, BeanDefinition bd, Object... args) throws BeanCreationException {
        Object bean = createBeanInstance(beanName, bd, args);
        // 创建成功，加入单例容器
        addSingleton(beanName, bean);
        return bean;
    }

    public Object createBeanInstance(String beanName, BeanDefinition bd, Object... args) {
        Class<?> beanClass = resolveBeanClass(bd, beanName);
        if (beanClass != null && !Modifier.isPublic(beanClass.getModifiers())) {
            throw new BeanCreationException(beanName,
                    "Bean class isn't public, and non-public access not allowed: " + beanClass.getName());
        }

        if (bd.hasConstructorArgumentValues() || !(args == null || args.length == 0)) {
            return autowireConstructor(beanName, bd, args);
        }

        return instantiateBean(beanName, bd);
    }

    public Class<?> resolveBeanClass(BeanDefinition bd, String beanName) throws CannotLoadBeanClassException {
        Class<?> beanClass;
        try {
            beanClass = bd.resolveBeanClass(null);
        } catch (ClassNotFoundException e) {
            throw new CannotLoadBeanClassException(beanName, bd.getBeanClassName(), e);
        }
        return beanClass;
    }

    public Object autowireConstructor(String beanName, BeanDefinition bd, Object... args) {
        Object[] argsToUse;
        if (args != null) {
            argsToUse = args;
        } else {
            argsToUse = bd.getConstructorArgumentValues().getGenericArgumentValues().stream().map(valueHolder -> valueHolder.getValue()).collect(Collectors.toList()).toArray(new Object[0]);
        }

        Constructor<?> constructorToUse = null;
        Class<?> beanClass = bd.getBeanClass();
        if (beanClass.isInterface()) {
            throw new BeanInstantiationException(beanClass, "Specified class is an interface");
        }
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == argsToUse.length) {
                constructorToUse = constructor;
            }
        }
        if (constructorToUse == null) {
            throw new BeanInstantiationException(beanClass, "No matched constructor found");
        }
        Object o;
        try {
            o = constructorToUse.newInstance(argsToUse);
        } catch (InstantiationException e) {
            throw new BeanInstantiationException(beanClass, "Is it an abstract class?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(beanClass, "Is the constructor accessible?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException(beanClass, "Constructor threw exception", e);
        }
        return o;
    }

    public Object instantiateBean(String beanName, BeanDefinition bd) {
        Class<?> beanClass = bd.getBeanClass();
        if (beanClass.isInterface()) {
            throw new BeanInstantiationException(beanClass, "Specified class is an interface");
        }
        Constructor<?> constructorToUse;
        try {
            constructorToUse = beanClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new BeanInstantiationException(beanClass, "No default constructor found", e);
        }
        Object o;
        try {
            o = constructorToUse.newInstance();
        } catch (InstantiationException e) {
            throw new BeanInstantiationException(beanClass, "Is it an abstract class?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(beanClass, "Is the constructor accessible?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException(beanClass, "Constructor threw exception", e);
        }
        // 如果创建了bean对象，把它添加到单例容器
        return o;
    }


    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

}
