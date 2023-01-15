package com.maoyou.springframework.aop.target;

import com.maoyou.springframework.aop.TargetSource;

/**
 * @ClassName SingletonTargetSource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/25 17:51
 * @Version 1.0
 */
public class SingletonTargetSource implements TargetSource {
    private final Object target;
    public SingletonTargetSource(Object target) {
        this.target = target;
    }
    @Override
    public Object getTarget() throws Exception {
        return target;
    }
    @Override
    public Class<?> getTargetClass() {
        return target.getClass();
    }
}
