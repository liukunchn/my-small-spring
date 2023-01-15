package com.maoyou.springframework.context;

import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.Aware;
import com.maoyou.springframework.context.support.AllInOneApplicationContext;

/**
 * @ClassName ApplicationContextAware
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/29 13:49
 * @Version 1.0
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(AllInOneApplicationContext applicationContext) throws BeansException;
}
