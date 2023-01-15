package com.maoyou.autowire;

import com.maoyou.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/18 17:15
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void testInstantiationAwareBeanPostProcessor() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-autowired.xml");
        UserDao userDao = context.getBean("userDaoImpl", UserDao.class);
        System.out.println(userDao);
        userDao.registerUser(new User("maoyou", "123456"));
        System.out.println(userDao.getUsers());
    }

    @org.junit.Test
    public void test2() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-autowired.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        System.out.println(userService);
        userService.registerUser(new User("maoyou", "123456"));
        System.out.println(userService.getUsers());
    }
}
