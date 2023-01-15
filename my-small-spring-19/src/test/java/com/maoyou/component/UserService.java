package com.maoyou.component;


import java.util.List;

/**
 * @ClassName UserService
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 16:42
 * @Version 1.0
 */
public interface UserService {
    void registerUser(User user);
    List<User> getUsers();
}
