package com.maoyou.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ClassName DefaultResourceLoader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 18:04
 * @Version 1.0
 */
public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResource(String location) {
        if (location.startsWith("/")) {
            return getResourceByPath(location);
        }
        else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return getResourceByPath(location);
            }
        }
    }

    public Resource getResourceByPath(String location) {
        return new ClassPathResource(location);
    }
}
