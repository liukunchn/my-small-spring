package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.Advisor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DefaultAdvisorAdapterRegistry
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/1 12:03
 * @Version 1.0
 */
public class DefaultAdvisorAdapterRegistry implements AdvisorAdapterRegistry {
    private final List<AdvisorAdapter> adapters = new ArrayList<>(3);

    public DefaultAdvisorAdapterRegistry() {
        registerAdvisorAdapter(new MethodBeforeAdviceAdapter());
        registerAdvisorAdapter(new AfterReturningAdviceAdapter());
        registerAdvisorAdapter(new ThrowsAdviceAdapter());
    }

    @Override
    public MethodInterceptor[] getInterceptors(Advisor advisor) {
        List<MethodInterceptor> interceptors = new ArrayList<>(3);
        Advice advice = advisor.getAdvice();
        if (advice instanceof MethodInterceptor) {
            interceptors.add((MethodInterceptor) advice);
        }
        for (AdvisorAdapter adapter : this.adapters) {
            if (adapter.supportsAdvice(advice)) {
                interceptors.add(adapter.getInterceptor(advisor));
            }
        }
        if (interceptors.isEmpty()) {
            throw new IllegalArgumentException("Advice object [" + advice + "] is neither a supported subinterface of " +
                    "[org.aopalliance.aop.Advice] nor an [org.springframework.aop.Advisor]");
        }
        return interceptors.toArray(new MethodInterceptor[0]);
    }

    @Override
    public void registerAdvisorAdapter(AdvisorAdapter adapter) {
        adapters.add(adapter);
    }
}
