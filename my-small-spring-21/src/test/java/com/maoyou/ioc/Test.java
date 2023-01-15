package com.maoyou.ioc;

import com.maoyou.springframework.beans.MutablePropertyValues;
import com.maoyou.springframework.beans.PropertyValue;
import com.maoyou.springframework.beans.PropertyValues;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanReference;
import com.maoyou.springframework.beans.factory.support.AllInOneBeanFactory;
import com.maoyou.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.maoyou.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.maoyou.springframework.context.ApplicationContext;
import com.maoyou.springframework.context.ConfigurableApplicationContext;
import com.maoyou.springframework.context.support.AllInOneApplicationContext;
import com.maoyou.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/15 12:39
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void test4() {
        AllInOneBeanFactory beanFactory = new AllInOneBeanFactory();

        BeanDefinition user = new BeanDefinition();
        user.setBeanClassName("com.maoyou.ioc.User");
        PropertyValue user_name = new PropertyValue("name", "张三");
        PropertyValue user_org = new PropertyValue("org", new BeanReference("org"));
        PropertyValues mutablePropertyValues = new MutablePropertyValues().addPropertyValue(user_name).addPropertyValue(user_org);
        user.setPropertyValues(mutablePropertyValues);
        beanFactory.registerBeanDefinition("user", user);

        BeanDefinition org = new BeanDefinition();
        org.setBeanClassName("com.maoyou.ioc.Org");
        PropertyValue org_name = new PropertyValue("name", "银海");
        PropertyValues mutablePropertyValues2 = new MutablePropertyValues().addPropertyValue(org_name);
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

    @org.junit.Test
    public void test5() {
        AllInOneBeanFactory beanFactory = new AllInOneBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        User user1 = beanFactory.getBean("user", User.class);
        System.out.println(user1);
        Org org1 = beanFactory.getBean("org", Org.class);
        System.out.println(org1);
        System.out.println(user1.getOrg() == org1);

        User user2 = beanFactory.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @org.junit.Test
    public void test7() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        User user1 = beanFactory.getBean("user", User.class);
        System.out.println(user1);
        Org org1 = beanFactory.getBean("org", Org.class);
        System.out.println(org1);
        System.out.println(user1.getOrg() == org1);

        User user2 = beanFactory.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @org.junit.Test
    public void test8() {
        AllInOneApplicationContext context = new AllInOneApplicationContext("classpath:spring.xml");

        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
        Org org1 = context.getBean("org", Org.class);
        System.out.println(org1);
        System.out.println(user1.getOrg() == org1);

        User user2 = context.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @org.junit.Test
    public void test9() {
        AllInOneApplicationContext context = new AllInOneApplicationContext("classpath:spring.xml");
        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
    }

    @org.junit.Test
    public void test10() {
        AllInOneApplicationContext context = new AllInOneApplicationContext("classpath:spring.xml");
        context.registerShutdownHook();

        User user1 = context.getBean("user", User.class);
        System.out.println(user1);

        context.close();
    }

    @org.junit.Test
    public void test11() {
        AllInOneApplicationContext context = new AllInOneApplicationContext("classpath:spring.xml");
        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
        System.out.println(user1.getApplicationContext());
        System.out.println(user1.getBeanFactory());
    }

    /**
     * 12.实现singleton,prototype作用域
     */
    @org.junit.Test
    public void test12() {
        AllInOneApplicationContext context = new AllInOneApplicationContext("classpath:spring.xml");
        context.registerShutdownHook();
        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
        User user2 = context.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

    @org.junit.Test
    public void test13() {
        AllInOneApplicationContext context = new AllInOneApplicationContext("classpath:spring.xml");
        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
        User userFactoryBean = context.getBean("userFactoryBean", User.class);
        System.out.println(userFactoryBean);
        User userFactoryBean2 = context.getBean("userFactoryBean", User.class);
        System.out.println(userFactoryBean2);
        System.out.println(userFactoryBean == userFactoryBean2);
    }

    @org.junit.Test
    public void test14() {
        AllInOneApplicationContext context = new AllInOneApplicationContext("classpath:spring.xml");
        context.registerShutdownHook();
        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
        context.publishEvent(new MyEvent("hh"));
    }

    @org.junit.Test
    public void test15() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        ((ConfigurableApplicationContext)context).registerShutdownHook();
        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
        context.publishEvent(new MyEvent("hh"));
    }

}
