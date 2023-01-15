package com.maoyou;

import com.maoyou.springframework.beans.MutablePropertyValues;
import com.maoyou.springframework.beans.PropertyValue;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanReference;

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

        BeanDefinition user = new BeanDefinition();
        user.setBeanClassName("com.maoyou.User");
        PropertyValue user_name = new PropertyValue("name", "张三");
        PropertyValue user_org = new PropertyValue("org", new BeanReference("org"));
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues().addPropertyValue(user_name).addPropertyValue(user_org);
        user.setPropertyValues(mutablePropertyValues);
        beanFactory.registerBeanDefinition("user", user);

        BeanDefinition org = new BeanDefinition();
        org.setBeanClassName("com.maoyou.Org");
        PropertyValue org_name = new PropertyValue("name", "银海");
        MutablePropertyValues mutablePropertyValues2 = new MutablePropertyValues().addPropertyValue(org_name);
        org.setPropertyValues(mutablePropertyValues2);
        beanFactory.registerBeanDefinition("org", org);

        User user1 = beanFactory.getBean("user", User.class);
        System.out.println(user1);
        Org org1 = beanFactory.getBean("org", Org.class);
        System.out.println(org1);
        System.out.println(user1.getOrg() == org1);

        User user2 = beanFactory.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }
}
