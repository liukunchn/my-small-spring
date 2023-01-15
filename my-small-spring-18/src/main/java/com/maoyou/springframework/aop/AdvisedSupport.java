package com.maoyou.springframework.aop;

import com.maoyou.springframework.aop.framework.AdvisorChainFactory;
import com.maoyou.springframework.aop.framework.DefaultAdvisorChainFactory;
import com.maoyou.springframework.aop.framework.ProxyConfig;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName AdvisedSupport
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 18:19
 * @Version 1.0
 */
public class AdvisedSupport extends ProxyConfig implements Advised {
    TargetSource targetSource;
    private List<Advisor> advisors = new ArrayList<>();
    AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

    @Override
    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    @Override
    public TargetSource getTargetSource() {
        return targetSource;
    }

    @Override
    public Class<?> getTargetClass() {
        return targetSource.getTargetClass();
    }

    @Override
    public Advisor[] getAdvisors() {
        return advisors.toArray(new Advisor[0]);
    }

    @Override
    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    public void addAdvisors(Collection<Advisor> advisors) {
        for (Advisor advisor : advisors) {
            this.advisors.add(advisor);
        }
    }

    public void addAdvisors(Advisor... advisors) {
        addAdvisors(Arrays.asList(advisors));
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        return advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass);
    }
}
