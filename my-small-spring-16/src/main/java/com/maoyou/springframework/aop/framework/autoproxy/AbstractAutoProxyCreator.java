package com.maoyou.springframework.aop.framework.autoproxy;

import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.Pointcut;
import com.maoyou.springframework.aop.framework.ProxyConfig;
import com.maoyou.springframework.aop.framework.ProxyFactory;
import com.maoyou.springframework.aop.target.SingletonTargetSource;
import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.BeanFactoryAware;
import com.maoyou.springframework.beans.factory.ListableBeanFactory;
import com.maoyou.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanPostProcessor;
import org.aopalliance.aop.Advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName AbstractAutoProxyCreator
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/29 9:19
 * @Version 1.0
 */
public abstract class AbstractAutoProxyCreator extends ProxyConfig implements BeanPostProcessor, BeanFactoryAware {
    private ListableBeanFactory beanFactory;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    public ListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        // 如果是AOP基础组件，应该直接返回
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        // 获取和目标对象适配的advisors
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
        // 如果advisors不为空，创建代理对象
        if (specificInterceptors != null) {
            Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
            return proxy;
        }
        // 否则，返回目标对象
        return bean;
    }

    protected boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass);
    }

    protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, Object customTargetSource);

    protected Object createProxy(Class<?> beanClass, String beanName, Object[] specificInterceptors, SingletonTargetSource targetSource) {
        // 将Object数组转化为Advisor数组
        Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
        // 创建ProxyFactory获取代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.copyFrom(this);
        proxyFactory.addAdvisors(advisors);
        proxyFactory.setTargetSource(targetSource);
        return proxyFactory.getProxy();
    }

    protected Advisor[] buildAdvisors(String beanName, Object[] specificInterceptors) {
        List<Object> allInterceptors = new ArrayList<>();
        if (specificInterceptors != null) {
            if (specificInterceptors.length > 0) {
                allInterceptors.addAll(Arrays.asList(specificInterceptors));
            }
        }
        Advisor[] advisors = new Advisor[allInterceptors.size()];
        for (int i = 0; i < allInterceptors.size(); i++) {
            advisors[i] = (Advisor) allInterceptors.get(i);
        }
        return advisors;
    }
}
