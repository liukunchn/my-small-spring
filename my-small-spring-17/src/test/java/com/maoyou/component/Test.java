package com.maoyou.component;

import aj.org.objectweb.asm.ClassReader;
import com.maoyou.springframework.context.support.ClassPathXmlApplicationContext;
import com.maoyou.springframework.core.annotation.MergedAnnotation;
import com.maoyou.springframework.core.annotation.MergedAnnotations;
import com.maoyou.springframework.core.io.ClassPathResource;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.maoyou.springframework.core.io.support.ResourcePatternResolver;
import com.maoyou.springframework.lang.NonNull;
import com.maoyou.springframework.util.AntPathMatcher;
import com.maoyou.springframework.util.ClassUtils;
import com.maoyou.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/3 10:43
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-component-scan.xml");
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        System.out.println(userService);
    }

    @org.junit.Test
    public void testAntPathMatcher() {
        PathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(pathMatcher.isPattern("/com/maoyou/component/UserServiceImpl.class"));//false
        System.out.println(pathMatcher.isPattern("/com/maoyou/component/**/*.class"));//true
        System.out.println(pathMatcher.match("/com/maoyou/component/**/*.class", "/com/maoyou/component/UserServiceImpl.class"));//true
        System.out.println(pathMatcher.matchStart("/com/maoyou/component/**/*.class", "/com/maoyou/component"));//true
    }

    @org.junit.Test
    public void testPathMatchingResourcePatternResolver() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath:com/maoyou/**/*.class");
        System.out.println(Arrays.toString(resources));
        for (Resource resource:resources) {
            System.out.println(resource);
        }
    }


}
