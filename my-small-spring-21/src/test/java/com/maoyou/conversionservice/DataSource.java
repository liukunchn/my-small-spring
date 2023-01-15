package com.maoyou.conversionservice;


import java.util.Date;
import java.util.Set;

/**
 * @ClassName DataSource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/2 14:20
 * @Version 1.0
 */
public class DataSource {
    private String url;
    private String username;
    private String password;
    private String driver;
    private Integer integer;
    private boolean bool;
    @DateFormat
    private Date date;
    private Set<Integer> ints;

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public Set<Integer> getInts() {
        return ints;
    }

    public void setInts(Set<Integer> ints) {
        this.ints = ints;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", driver='" + driver + '\'' +
                ", integer=" + integer +
                ", bool=" + bool +
                ", date=" + date +
                ", ints=" + ints +
                '}';
    }
}
