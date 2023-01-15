package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.AfterAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ThrowsAdviceInterceptor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 10:59
 * @Version 1.0
 */
public class ThrowsAdviceInterceptor implements MethodInterceptor, AfterAdvice {
    private static final String AFTER_THROWING = "afterThrowing";
    private final Object throwsAdvice;
    private final Map<Class<?>, Method> exceptionHandlerMap = new HashMap<>();

    public ThrowsAdviceInterceptor(Object throwsAdvice) {
        this.throwsAdvice = throwsAdvice;
        Method[] methods = throwsAdvice.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(AFTER_THROWING) &&
                    (method.getParameterCount() == 1 || method.getParameterCount() == 4)) {
                Class<?> throwableParam = method.getParameterTypes()[method.getParameterCount() - 1];
                if (Throwable.class.isAssignableFrom(throwableParam)) {
                    this.exceptionHandlerMap.put(throwableParam, method);
                }
            }
        }
        if (this.exceptionHandlerMap.isEmpty()) {
            throw new IllegalArgumentException("At least one handler method must be found in class [" + throwsAdvice.getClass() + "]");
        }
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        }
        catch (Throwable th) {
            Method handlerMethod = getExceptionHandler(th);
            if (handlerMethod != null) {
                invokeHandlerMethod(mi, th, handlerMethod);
            }
            throw th;
        }
    }

    private void invokeHandlerMethod(MethodInvocation mi, Throwable th, Method method) throws Throwable {
        Object[] handlerArgs;
        if (method.getParameterCount() == 1) {
            handlerArgs = new Object[] {th};
        }
        else {
            handlerArgs = new Object[] {mi.getMethod(), mi.getArguments(), mi.getThis(), th};
        }
        try {
            method.invoke(this.throwsAdvice, handlerArgs);
        }
        catch (InvocationTargetException targetEx) {
            throw targetEx.getTargetException();
        }
    }

    private Method getExceptionHandler(Throwable th) {
        Class<?> exceptionClass = th.getClass();
        Method handler = this.exceptionHandlerMap.get(exceptionClass);
        while (handler == null && exceptionClass != Throwable.class) {
            exceptionClass = exceptionClass.getSuperclass();
            handler = this.exceptionHandlerMap.get(exceptionClass);
        }
        return handler;
    }
}
