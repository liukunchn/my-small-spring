package com.maoyou.springframework.core.io;

import com.maoyou.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @ClassName FileSystemResource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 17:44
 * @Version 1.0
 */
public class FileSystemResource implements Resource {
    private final String path;

    private final File file;

    public FileSystemResource(String path) {
        // 如果没有使用文件系统资源加载器，如果path="/test.txt"，依然会是根磁盘下面的test.txt
        this.path = path;
        this.file = new File(path);
    }

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    @Override
    public InputStream getInputStrem() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public URL getURL() throws IOException {
        return (this.file != null ? this.file.toURI().toURL() : null);
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        String pathToUse = StringUtils.applyRelativePath(this.path, relativePath);
        return (this.file != null ? new FileSystemResource(pathToUse) :
                null);
    }

    @Override
    public File getFile() throws IOException {
        return (this.file != null ? this.file : null);
    }

    @Override
    public String toString() {
        return "FileSystemResource{" +
                "file=" + file +
                '}';
    }
}
