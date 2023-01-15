package com.maoyou.springframework.context.support;

import com.maoyou.springframework.core.io.Resource;

/**
 * @ClassName AbstractRefreshableConfigApplicationContext
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 17:46
 * @Version 1.0
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {
    private String[] configLocations;

    private Resource[] configResources;

    public String[] getConfigLocations() {
        return configLocations;
    }

    public void setConfigLocations(String[] configLocations) {
        this.configLocations = configLocations;
    }

    public Resource[] getConfigResources() {
        return configResources;
    }

    public void setConfigResources(Resource[] configResources) {
        this.configResources = configResources;
    }
}
