package com.maoyou.springframework.core.io.support;

import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.io.ResourceLoader;

import java.io.IOException;

/**
 * @ClassName ResourcePatternResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/3 12:26
 * @Version 1.0
 */
public interface ResourcePatternResolver extends ResourceLoader {
    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    Resource[] getResources(String locationPattern) throws IOException;
}
