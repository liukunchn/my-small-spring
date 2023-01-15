package com.maoyou.springframework.core.io;

import com.maoyou.springframework.util.ClassUtils;

/**
 * @ClassName FileSystemResourceLoader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 18:13
 * @Version 1.0
 */
public class FileSystemResourceLoader extends DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResourceByPath(String path) {
        // 如果path="/test.txt"，将不会是根磁盘下面的test.txt，而是程序工作目录下面的test.txt
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return new FileSystemResource(path);
    }

    @Override
    public ClassLoader getClassLoader() {
        return super.getClassLoader();
    }
}
