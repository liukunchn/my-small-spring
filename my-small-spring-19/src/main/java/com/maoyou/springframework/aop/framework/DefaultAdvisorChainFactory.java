package com.maoyou.springframework.aop.framework;

import com.maoyou.springframework.aop.Advised;
import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.MethodMatcher;
import com.maoyou.springframework.aop.PointcutAdvisor;
import com.maoyou.springframework.aop.adapter.AdvisorAdapterRegistry;
import com.maoyou.springframework.aop.adapter.DefaultAdvisorAdapterRegistry;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName DefaultAdvisorChainFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 15:32
 * @Version 1.0
 */
public class DefaultAdvisorChainFactory implements AdvisorChainFactory {
    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advisor[] advisors, Method method, Class<?> targetClass) {
        List<Object> interceptorList = new ArrayList<>(advisors.length);
        Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
        for (Advisor advisor : advisors) {
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                if (pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
                    MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
                    boolean match = mm.matches(method, actualClass);
                    if (match) {
                        MethodInterceptor[] interceptors = new MethodInterceptor[] {(MethodInterceptor) advisor.getAdvice()};
                        interceptorList.addAll(Arrays.asList(interceptors));
                    }
                }
            }
        }
        return interceptorList;
    }

    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Advised advised, Method method, Class<?> targetClass) {
        AdvisorAdapterRegistry registry = new DefaultAdvisorAdapterRegistry();
        Advisor[] advisors = advised.getAdvisors();
        List<Object> interceptorList = new ArrayList<>(advisors.length);
        Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
        for (Advisor advisor : advisors) {
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                if (pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
                    MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
                    boolean match = mm.matches(method, actualClass);
                    if (match) {
                        MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
                        interceptorList.addAll(Arrays.asList(interceptors));
                    }
                }
            }
        }
        return interceptorList;
    }
}
