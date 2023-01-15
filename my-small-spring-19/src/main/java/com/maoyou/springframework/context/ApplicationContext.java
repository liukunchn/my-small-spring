package com.maoyou.springframework.context;

import com.maoyou.springframework.beans.factory.ListableBeanFactory;
import com.maoyou.springframework.core.env.Environment;
import com.maoyou.springframework.core.env.EnvironmentCapable;
import com.maoyou.springframework.core.io.ResourceLoader;

/**
 * @ClassName ApplicationContext
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 17:23
 * @Version 1.0
 */
public interface ApplicationContext extends ListableBeanFactory, ApplicationEventPublisher, ResourceLoader, EnvironmentCapable {
}
