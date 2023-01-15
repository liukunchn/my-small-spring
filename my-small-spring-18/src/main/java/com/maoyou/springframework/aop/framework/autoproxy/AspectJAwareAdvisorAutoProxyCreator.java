package com.maoyou.springframework.aop.framework.autoproxy;

import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.MethodMatcher;
import com.maoyou.springframework.aop.Pointcut;
import com.maoyou.springframework.aop.PointcutAdvisor;
import com.maoyou.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @ClassName AspectJAwareAdvisorAutoProxyCreator
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/29 9:20
 * @Version 1.0
 */
public class AspectJAwareAdvisorAutoProxyCreator extends AbstractAutoProxyCreator {
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, Object customTargetSource) {
        List<Advisor> advisors = findEligibleAdvisors(beanClass, beanName);
        if (advisors.isEmpty()) {
            return null;
        }
        return advisors.toArray();
    }

    protected List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
        // 找到候选的advisors
        List<Advisor> candidateAdvisors = findCandidateAdvisors();
        // 找到和目标对象匹配的advisors
        List<Advisor> eligibleAdvisors = findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);
        // todo 排序
        return eligibleAdvisors;
    }

    private List<Advisor> findCandidateAdvisors() {
        Map<String, Advisor> beansOfType = getBeanFactory().getBeansOfType(Advisor.class);
        return new ArrayList<>(beansOfType.values());
    }

    private List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> beanClass, String beanName) {
        if (candidateAdvisors.isEmpty()) {
            return candidateAdvisors;
        }
        List<Advisor> eligibleAdvisors = new ArrayList<>();
        for (Advisor candidate : candidateAdvisors) {
            if (canApply(candidate, beanClass)) {
                eligibleAdvisors.add(candidate);
            }
        }
        return eligibleAdvisors;
    }

    private boolean canApply(Advisor advisor, Class<?> targetClass) {
        if (advisor instanceof PointcutAdvisor) {
            PointcutAdvisor pca = (PointcutAdvisor) advisor;
            return canApply(pca.getPointcut(), targetClass);
        }
        else {
            // It doesn't have a pointcut so we assume it applies.
            return true;
        }
    }

    private boolean canApply(Pointcut pc, Class<?> targetClass) {
        // clazz不匹配返回false
        if (!pc.getClassFilter().matches(targetClass)) {
            return false;
        }

        // 获取类本身+类以及所有父类的所有接口
        Set<Class<?>> classes = new LinkedHashSet<>();
        if (!Proxy.isProxyClass(targetClass)) {
            classes.add(targetClass);
        }
        classes.addAll(ClassUtils.getAllInterfacesForClassAsSet(targetClass));

        // 任意一个方法匹配返回true
        MethodMatcher methodMatcher = pc.getMethodMatcher();
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (methodMatcher.matches(method, targetClass)) {
                    return true;
                }
            }
        }
        return false;
    }
}
