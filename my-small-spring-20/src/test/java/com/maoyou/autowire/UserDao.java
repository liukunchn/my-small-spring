package com.maoyou.autowire;

import java.util.List;

/**
 * @ClassName UserDao
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/25 22:54
 * @Version 1.0
 */
public interface UserDao {
    void registerUser(User user);
    List<User> getUsers();
}
