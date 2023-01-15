package com.maoyou;

import com.maoyou.springframework.beans.factory.FactoryBean;

/**
 * @ClassName UserFactoryBean
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 20:56
 * @Version 1.0
 */
public class UserFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
