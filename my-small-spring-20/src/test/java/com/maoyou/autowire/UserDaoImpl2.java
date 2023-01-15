package com.maoyou.autowire;

import com.maoyou.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserDaoImpl
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/25 22:55
 * @Version 1.0
 */
public class UserDaoImpl2 implements UserDao {
    public final static List<User> users = new ArrayList<>();

    private UserDao userDao;

    @Override
    public void registerUser(User user) {
        users.add(user);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
