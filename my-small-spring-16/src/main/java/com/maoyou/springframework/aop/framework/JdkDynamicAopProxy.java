package com.maoyou.springframework.aop.framework;

import com.maoyou.springframework.aop.AdvisedSupport;
import com.maoyou.springframework.aop.TargetSource;
import com.maoyou.springframework.util.ClassUtils;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @ClassName JdkDynamicAopProxy
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 18:22
 * @Version 1.0
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        try {
            createProxyClassFile(advised.getTargetSource().getTarget());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), advised.getTargetSource().getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TargetSource targetSource = advised.getTargetSource();
        Object target = targetSource.getTarget();
        Class<?> targetClass = (target != null ? target.getClass() : null);
        List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
        ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(targetSource.getTarget(), method, args, chain);
        Object object = reflectiveMethodInvocation.proceed();
        return object;
    }

    private static void createProxyClassFile(Object target){
        byte[] data = ProxyGenerator.generateProxyClass("Proxy$"+target.getClass().getSimpleName(),target.getClass().getInterfaces());
        FileOutputStream out =null;
        try {
            out = new FileOutputStream("Proxy$"+target.getClass().getSimpleName()+".class");
            out.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
