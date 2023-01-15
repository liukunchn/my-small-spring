package com.maoyou;

import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.ConstructorArgumentValues;

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
//        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
//        constructorArgumentValues.addValueHolder(new ConstructorArgumentValues.ValueHolder("name", "查询"));
//        beanDifinition.setConstructorArgumentValues(constructorArgumentValues);
        beanFactory.registerBeanDefinition("user", beanDifinition);

        User user1 = (User) beanFactory.getBean("user", "保存");
        System.out.println(user1);
        System.out.println(user1.getName());
        User user2 = beanFactory.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user2.getName());
    }
}
