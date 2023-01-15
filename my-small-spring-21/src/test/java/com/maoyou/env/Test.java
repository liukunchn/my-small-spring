package com.maoyou.env;

import com.maoyou.ioc.User;
import com.maoyou.springframework.context.ApplicationContext;
import com.maoyou.springframework.context.support.ClassPathXmlApplicationContext;
import com.maoyou.springframework.core.env.*;
import com.maoyou.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/21 15:36
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void testPropertiesPropertySource() throws IOException {
        Properties properties = new Properties();
        properties.load(ClassUtils.getDefaultClassLoader().getResourceAsStream("datasource.properties"));
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource("dataSourceProperties", properties);
        System.out.println(Arrays.toString(propertiesPropertySource.getPropertyNames()));
    }

    @org.junit.Test
    public void testSystemEnvironmentPropertySource() {
        Map<String, String> env = System.getenv();
        SystemEnvironmentPropertySource systemEnvironmentPropertySource = new SystemEnvironmentPropertySource("systemEnvironmentPropertySource", (Map)env);
        System.out.println(Arrays.toString(systemEnvironmentPropertySource.getPropertyNames()));
        System.out.println(systemEnvironmentPropertySource.getProperty("userdomain.roamingprofile"));
    }

    @org.junit.Test
    public void testMutablePropertySources() throws IOException {
        Properties properties = new Properties();
        properties.load(ClassUtils.getDefaultClassLoader().getResourceAsStream("datasource.properties"));
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addLast(new PropertiesPropertySource("dataSourceProperties", properties));
        propertySources.addLast(new PropertiesPropertySource("systemProperties", System.getProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment", (Map)System.getenv()));
        System.out.println(propertySources.contains("systemEnvironment"));
        System.out.println(propertySources.get("systemEnvironment"));
    }

    @org.junit.Test
    public void testPropertySourcesPropertyResolver() throws IOException {
        Properties properties = new Properties();
        properties.load(ClassUtils.getDefaultClassLoader().getResourceAsStream("datasource.properties"));
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addLast(new PropertiesPropertySource("dataSourceProperties", properties));
        propertySources.addLast(new PropertiesPropertySource("systemProperties", System.getProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment", (Map)System.getenv()));
        PropertyResolver resolver = new PropertySourcesPropertyResolver(propertySources);
        System.out.println(resolver.containsProperty("port"));
        System.out.println(resolver.getProperty("username"));
        System.out.println(resolver.getRequiredProperty("url"));
        System.out.println(resolver.resolvePlaceholders("The driver is ${driver}"));
        System.out.println(resolver.resolveRequiredPlaceholders("The url is ${url}"));
    }

    @org.junit.Test
    public void testStandardEnvironment() {
        Environment environment = new StandardEnvironment();
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
        System.out.println(Arrays.toString(environment.getDefaultProfiles()));
        System.out.println(environment.acceptsProfiles(new String[]{"dev", "test"}));
        System.out.println(System.getenv().keySet());
        System.out.println(environment.getProperty("USERDOMAIN_ROAMINGPROFILE"));
    }

    @org.junit.Test
    public void testEnvironment() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Environment environment = context.getEnvironment();
        System.out.println(Arrays.toString(environment.getDefaultProfiles()));
        User user = context.getBean("user", User.class);
        System.out.println(Arrays.toString(user.getEnvironment().getActiveProfiles()));
    }

    @org.junit.Test
    public void testPropertySourcesPlaceholderConfigurer() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-placeholder.xml");
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println(dataSource);
    }

}
