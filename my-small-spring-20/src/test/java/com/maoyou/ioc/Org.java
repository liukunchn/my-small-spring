package com.maoyou.ioc;

/**
 * @ClassName Org
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/17 14:06
 * @Version 1.0
 */
public class Org {
    private String name;

    public Org() {
    }

    public Org(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Org{" +
                "name='" + name + '\'' +
                '}';
    }
}
