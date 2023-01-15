package com.maoyou;

import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 12:39
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();

        BeanDefinition beanDifinition = new BeanDefinition();
        beanDifinition.setBeanClassName("com.maoyou.User");
        beanFactory.registerBeanDefinition("user", beanDifinition);

        User user1 = beanFactory.getBean("user", User.class);
        System.out.println(user1);
        User user2 = beanFactory.getBean("user", User.class);
        System.out.println(user2);
    }
}
