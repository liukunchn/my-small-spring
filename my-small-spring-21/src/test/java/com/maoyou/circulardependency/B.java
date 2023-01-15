package com.maoyou.circulardependency;

/**
 * @ClassName B
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/3/7 22:13
 * @Version 1.0
 */
public class B {
    public A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

}
