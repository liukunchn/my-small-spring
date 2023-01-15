package com.maoyou.springframework.context.support;

/**
 * @ClassName ClassPathXmlApplicationContext
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/12 17:47
 * @Version 1.0
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    public ClassPathXmlApplicationContext(String... configLocations) {
        setConfigLocations(configLocations);
        refresh();
    }
}
