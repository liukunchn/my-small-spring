package com.maoyou.circulardependency;

/**
 * @ClassName A
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/3/7 22:13
 * @Version 1.0
 */
public class A {
    public B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

}
