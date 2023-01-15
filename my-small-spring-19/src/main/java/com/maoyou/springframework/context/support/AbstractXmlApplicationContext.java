package com.maoyou.springframework.context.support;

import com.maoyou.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.maoyou.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.maoyou.springframework.core.io.Resource;

/**
 * @ClassName AbstractXmlApplicationContext
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 17:47
 * @Version 1.0
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        Resource[] configResources = this.getConfigResources();
        if (configResources != null) {
            reader.loadBeanDefinitions(configResources);
        }

        String[] configLocations = this.getConfigLocations();
        if (configLocations != null) {
            reader.loadBeanDefinitions(configLocations);
        }
    }
}
