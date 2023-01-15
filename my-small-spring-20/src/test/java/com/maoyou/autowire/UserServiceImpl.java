package com.maoyou.autowire;


import com.maoyou.springframework.beans.factory.annotation.Autowired;
import com.maoyou.springframework.beans.factory.annotation.Value;
import com.maoyou.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 16:45
 * @Version 1.0
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Value("${username}")
    private String username;

    @Override
    public void registerUser(User user) {
        userDao.registerUser(user);
    }

    @Override
    public List<User> getUsers() {
        System.out.println(username);
        return userDao.getUsers();
    }
}
