package com.maoyou;

import com.maoyou.springframework.beans.MutablePropertyValues;
import com.maoyou.springframework.beans.PropertyValue;
import com.maoyou.springframework.beans.factory.support.AllInOneBeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanReference;
import com.maoyou.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.maoyou.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 12:39
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void test1() {
        AllInOneBeanFactory AllInOneBeanFactory = new AllInOneBeanFactory();

        BeanDefinition user = new BeanDefinition();
        user.setBeanClassName("com.maoyou.User");
        PropertyValue user_name = new PropertyValue("name", "张三");
        PropertyValue user_org = new PropertyValue("org", new BeanReference("org"));
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues().addPropertyValue(user_name).addPropertyValue(user_org);
        user.setPropertyValues(mutablePropertyValues);
        AllInOneBeanFactory.registerBeanDefinition("user", user);

        BeanDefinition org = new BeanDefinition();
        org.setBeanClassName("com.maoyou.Org");
        PropertyValue org_name = new PropertyValue("name", "银海");
        MutablePropertyValues mutablePropertyValues2 = new MutablePropertyValues().addPropertyValue(org_name);
        org.setPropertyValues(mutablePropertyValues2);
        AllInOneBeanFactory.registerBeanDefinition("org", org);

        User user1 = AllInOneBeanFactory.getBean("user", User.class);
        System.out.println(user1);
        Org org1 = AllInOneBeanFactory.getBean("org", Org.class);
        System.out.println(org1);
        System.out.println(user1.getOrg() == org1);

        User user2 = AllInOneBeanFactory.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @org.junit.Test
    public void test2() {
        AllInOneBeanFactory AllInOneBeanFactory = new AllInOneBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(AllInOneBeanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        User user1 = AllInOneBeanFactory.getBean("user", User.class);
        System.out.println(user1);
        Org org1 = AllInOneBeanFactory.getBean("org", Org.class);
        System.out.println(org1);
        System.out.println(user1.getOrg() == org1);

        User user2 = AllInOneBeanFactory.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @org.junit.Test
    public void test3() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        User user1 = defaultListableBeanFactory.getBean("user", User.class);
        System.out.println(user1);
        Org org1 = defaultListableBeanFactory.getBean("org", Org.class);
        System.out.println(org1);
        System.out.println(user1.getOrg() == org1);

        User user2 = defaultListableBeanFactory.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @org.junit.Test
    public void test4() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        Map<String, User> beansOfType = defaultListableBeanFactory.getBeansOfType(User.class);
        System.out.println(beansOfType);
    }
}
