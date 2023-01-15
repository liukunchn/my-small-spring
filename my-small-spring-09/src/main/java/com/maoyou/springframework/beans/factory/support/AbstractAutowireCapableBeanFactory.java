package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.BeanCreationException;
import com.maoyou.springframework.beans.BeanInstantiationException;
import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.MethodInvocationException;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import com.maoyou.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;

/**
 * @ClassName AbstractAutowireCapableBeanFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 19:04
 * @Version 1.0
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(String beanName, BeanDefinition bd, Object[] args) throws BeansException {
        try {
            Object beanInstance = doCreateBean(beanName, bd, args);
            return beanInstance;
        } catch (BeanCreationException e) {
            throw e;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Unexpected exception during bean creation", ex);
        }
    }

    protected Object doCreateBean(String beanName, BeanDefinition bd, Object[] args) throws BeanCreationException {
        // 创建bean实例
        Object bean = createBeanInstance(beanName, bd, args);
        // 缓存单例
        registerSingleton(beanName, bean);
        // 填充属性和依赖
        populateBean(beanName, bd, bean);
        // 初始化bean
        bean = initializeBean(beanName, bean, bd);
        return bean;
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition bd) {
        bean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        invokeInitMethods(beanName, bean, bd);
        bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return bean;
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition bd) {
        // todo
    }

    public Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor: getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    protected Object createBeanInstance(String beanName, BeanDefinition bd, Object... args) {
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

    protected Object autowireConstructor(String beanName, BeanDefinition bd, Object... args) {
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

    protected Object instantiateBean(String beanName, BeanDefinition bd) {
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

    protected void populateBean(String beanNaem, BeanDefinition bd, Object bean) {
        bd.getPropertyValues().getPropertyValueList().forEach(propertyValue -> {
            String setterName = "set" + propertyValue.getName().substring(0, 1).toUpperCase() + propertyValue.getName().substring(1);
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                value = getBean(((BeanReference) value).getBeanName());
            }
            try {
                Method setter = bd.getBeanClass().getDeclaredMethod(setterName, value.getClass());
                setter.invoke(bean, value);
            } catch (Throwable e) {
                // todo MethodInvocationException使用了事件机制，完成事件支持后再修改这里
                throw new MethodInvocationException("methodInvocationException", e);
            }

        });
    }

}
