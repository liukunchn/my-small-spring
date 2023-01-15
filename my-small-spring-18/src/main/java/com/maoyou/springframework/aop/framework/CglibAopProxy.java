package com.maoyou.springframework.aop.framework;

import com.maoyou.springframework.aop.AdvisedSupport;
import com.maoyou.springframework.aop.TargetSource;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName CglibAopProxy
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 16:10
 * @Version 1.0
 */
public class CglibAopProxy implements AopProxy {
    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        Object proxy = enhancer.create();
        return proxy;
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            TargetSource targetSource = advised.getTargetSource();
            Object target = targetSource.getTarget();
            Class<?> targetClass = (target != null ? target.getClass() : null);
            List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, objects,chain, methodProxy);
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, List<?> interceptorsAndDynamicMethodMatchers,  MethodProxy methodProxy) {
            super(target, method, arguments, interceptorsAndDynamicMethodMatchers);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
//            if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
//                return methodProxy.invoke(target, args);
//            }
//            Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
//            return ((org.aopalliance.intercept.MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
            return super.proceed();
        }
    }
}
