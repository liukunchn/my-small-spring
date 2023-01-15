package com.maoyou.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName ReflectiveMethodInvocation
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 17:56
 * @Version 1.0
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object target;
    protected final Method method;
    protected Object[] args;
    protected final List<?> interceptorsAndDynamicMethodMatchers;
    protected int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] args, List<?> interceptorsAndDynamicMethodMatchers) {
        this.target = target;
        this.method = method;
        this.args = args;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return method.invoke(target, args);
        }
        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
