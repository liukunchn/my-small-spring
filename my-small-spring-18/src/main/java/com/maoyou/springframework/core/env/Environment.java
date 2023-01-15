package com.maoyou.springframework.core.env;

/**
 * @ClassName Environment
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/21 23:50
 * @Version 1.0
 */
public interface Environment {
    String[] getActiveProfiles();


    String[] getDefaultProfiles();

    @Deprecated
    boolean acceptsProfiles(String... profiles);

//    boolean acceptsProfiles(Profiles profiles);
}
