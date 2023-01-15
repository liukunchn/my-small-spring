package com.maoyou.springframework.core.io;

import com.maoyou.springframework.util.ResourceUtils;
import com.maoyou.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @ClassName ClassPathResource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 17:48
 * @Version 1.0
 */
public class ClassPathResource implements Resource {
    private final String path;

    private ClassLoader classLoader;

    private Class<?> clazz;

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    public ClassPathResource(String path,ClassLoader classLoader) {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        // 因为classLoader.getResourceAsStream("/xxx")，将获取不到，返回null
        this.path = path.startsWith("/") ? path.substring(1) : path;
        this.classLoader = (classLoader != null ? classLoader : getDefaultClassLoader());
    }

    public ClassPathResource(String path, Class<?> clazz) {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        this.path = path;
        this.clazz = clazz;
    }

    private ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
        }
        if (cl == null) {
            cl = ClassPathResource.class.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                }
            }
        }
        return cl;
    }

    @Override
    public InputStream getInputStrem() throws IOException {
        InputStream is;
        if (this.clazz != null) {
            is = this.clazz.getResourceAsStream(this.path);
        }
        else if (this.classLoader != null) {
            is = this.classLoader.getResourceAsStream(this.path);
        }
        else {
            is = ClassLoader.getSystemResourceAsStream(this.path);
        }
        if (is == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return is;
    }

    @Override
    public URL getURL() throws IOException {
        URL url = resolveURL();
        if (url == null) {
            throw new FileNotFoundException(" cannot be resolved to URL because it does not exist");
        }
        return url;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        String pathToUse = StringUtils.applyRelativePath(this.path, relativePath);
        return (this.clazz != null ? new ClassPathResource(pathToUse, this.clazz) :
                new ClassPathResource(pathToUse, this.classLoader));
    }

    @Override
    public File getFile() throws IOException {
        URL url = getURL();

        return ResourceUtils.getFile(url, "");
    }

    private URL resolveURL() {
        try {
            if (this.clazz != null) {
                return this.clazz.getResource(this.path);
            }
            else if (this.classLoader != null) {
                return this.classLoader.getResource(this.path);
            }
            else {
                return ClassLoader.getSystemResource(this.path);
            }
        }
        catch (IllegalArgumentException ex) {
            // Should not happen according to the JDK's contract:
            // see https://github.com/openjdk/jdk/pull/2662
            return null;
        }
    }

    @Override
    public String toString() {
        return "ClassPathResource{" +
                "path='" + path + '\'' +
                '}';
    }
}
