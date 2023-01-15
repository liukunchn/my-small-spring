package com.maoyou.springframework.context;

import com.maoyou.springframework.beans.factory.Aware;
import com.maoyou.springframework.core.env.Environment;

/**
 * @ClassName EnvironmentAware
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 11:15
 * @Version 1.0
 */
public interface EnvironmentAware extends Aware {
    void setEnvironment(Environment environment);
}
