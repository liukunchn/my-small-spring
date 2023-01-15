package com.maoyou.circulardependency;


import com.maoyou.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/3/7 22:13
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-circulardependency.xml");
        A a = applicationContext.getBean("a", A.class);
        B b = applicationContext.getBean("b", B.class);
        System.out.println("A：" + a);
        System.out.println("A.B：" + a.getB());
        System.out.println("B：" + b);
        System.out.println("B.A：" + b.getA());
    }
}
