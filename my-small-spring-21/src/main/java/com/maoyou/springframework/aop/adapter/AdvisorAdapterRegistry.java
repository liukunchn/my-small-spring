package com.maoyou.springframework.aop.adapter;

import com.maoyou.springframework.aop.Advisor;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @ClassName AdvisorAdapterRegistry
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 15:44
 * @Version 1.0
 */
public interface AdvisorAdapterRegistry {
    MethodInterceptor[] getInterceptors(Advisor advisor);
    void registerAdvisorAdapter(AdvisorAdapter adapter);
}
