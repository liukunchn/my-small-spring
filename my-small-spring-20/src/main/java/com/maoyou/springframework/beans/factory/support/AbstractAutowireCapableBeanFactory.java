package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.*;
import com.maoyou.springframework.beans.factory.*;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import com.maoyou.springframework.beans.factory.config.BeanReference;
import com.maoyou.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.maoyou.springframework.core.MethodParameter;
import com.maoyou.springframework.core.convert.ConversionService;
import com.maoyou.springframework.core.convert.TypeDescriptor;
import com.maoyou.springframework.core.convert.support.DefaultConversionService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
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
            // 给BeanPostProcessors一个机会，在doCreateBean()方法之前返回一个代理对象
            Object bean = resolveBeforeInstantiation(beanName, bd);
            if (bean != null) {
                return bean;
            }
        }
        catch (Throwable ex) {
            throw new BeanCreationException(beanName,
                    "BeanPostProcessor before instantiation of bean failed", ex);
        }
        try {
            Object beanInstance = doCreateBean(beanName, bd, args);
            return beanInstance;
        } catch (BeanCreationException e) {
            throw e;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Unexpected exception during bean creation", ex);
        }
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition bd) {
        Object bean = null;
        if (hasInstantiationAwareBeanPostProcessors()) {
            // 决定目标类型（调用SmartInstantiationAwareBeanPostProcessor的predictBeanType()方法）,这里简单改成bd中的类型
            // Class<?> targetType = determineTargetType(beanName, bd);
            Class<?> targetType = resolveBeanClass(bd, beanName);;
            bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
            if (bean != null) {
                bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
            }
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (InstantiationAwareBeanPostProcessor bp : getInstantiationAwareBeanPostProcessors()) {
            Object result = bp.postProcessBeforeInstantiation(beanClass, beanName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }


    protected Object doCreateBean(String beanName, BeanDefinition bd, Object[] args) throws BeanCreationException {
        // 创建bean实例
        Object bean = createBeanInstance(beanName, bd, args);

        // 填充属性和依赖
        populateBean(beanName, bd, bean);
        // 初始化bean
        bean = initializeBean(beanName, bean, bd);
        // 如果必要，注册DisposableBean
        registerDisposableBeanIfNecessary(beanName, bean, bd);
        return bean;
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition bd) {
        invokeAwareMethods(beanName, bean);
        bean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        try {
            invokeInitMethods(beanName, bean, bd);
        } catch (Throwable e) {
            throw new BeanCreationException(beanName, "Invocation of init method failed", e);
        }
        bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return bean;
    }

    private void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ClassLoader bcl = getBeanClassLoader();
                if (bcl != null) {
                    ((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
                }
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
            }
        }
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition bd) throws Throwable {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        if (bd.getInitMethodName() != null) {
            bean.getClass().getDeclaredMethod(bd.getInitMethodName()).invoke(bean);
        }
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

    protected void populateBean(String beanName, BeanDefinition bd, Object bean) {
        // 如果任何一个InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation()返回false，方法短路
        if (hasInstantiationAwareBeanPostProcessors()) {
            for (InstantiationAwareBeanPostProcessor bp : getInstantiationAwareBeanPostProcessors()) {
                if (!bp.postProcessAfterInstantiation(bean, beanName)) {
                    return;
                }
            }
        }

        PropertyValues pvs = bd.getPropertyValues();

        // 在applyPropertyValues()执行之前修改pvs
        if (hasInstantiationAwareBeanPostProcessors()) {
            for (InstantiationAwareBeanPostProcessor bp : getInstantiationAwareBeanPostProcessors()) {
                PropertyValues pvsToUse = bp.postProcessProperties(pvs, bean, beanName);
                pvs = pvsToUse;
            }
        }

        // 应用属性值
        if (pvs != null) {
            applyPropertyValues(beanName, bd, bean, pvs);
        }

    }

    protected void applyPropertyValues(String beanName, BeanDefinition bd, Object bean, PropertyValues pvs) {
        pvs.getPropertyValueList().forEach(propertyValue -> {
            String fieldName =  propertyValue.getName();
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                value = getBean(((BeanReference) value).getBeanName());
            }
            if (value instanceof Set) {
                Set<?> set = (Set<?>) value;
                Set<Object> newSet = new HashSet();
                for (Object obj:set) {
                    if (obj instanceof BeanReference) {
                        obj = getBean(((BeanReference) obj).getBeanName());
                    }
                    newSet.add(obj);
                }
                value = newSet;
            }
            ConversionService conversionService = getConversionService();
            if (conversionService == null) {
                conversionService = new DefaultConversionService();
            }
            try {
                Method method = bd.getDescriptors().get(fieldName);
                if (method == null) {
                    throw new NoSuchMethodException(fieldName);
                }
                value = conversionService.convert(value, TypeDescriptor.forObject(value), new TypeDescriptor(new MethodParameter(method, 0)));
                method.invoke(bean, value);
            } catch (Throwable e) {
                // todo MethodInvocationException使用了事件机制，完成事件支持后再修改这里
                throw new MethodInvocationException("methodInvocationException", e);
            }

        });
    }

}
