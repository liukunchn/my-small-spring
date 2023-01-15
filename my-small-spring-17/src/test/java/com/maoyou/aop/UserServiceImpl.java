package com.maoyou.aop;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 16:45
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {
    public final static List<User> users = new ArrayList<>();
    @Override
    public void registerUser(User user) {
        users.add(user);
        System.out.println("添加成功");
//        throw new RuntimeException("测试");
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
