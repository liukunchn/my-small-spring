package com.maoyou.env;

/**
 * @ClassName DataSource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 17:31
 * @Version 1.0
 */
public class DataSource {
    private String username;
    private String password;
    private String url;

    @Override
    public String toString() {
        return "DataSource{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
