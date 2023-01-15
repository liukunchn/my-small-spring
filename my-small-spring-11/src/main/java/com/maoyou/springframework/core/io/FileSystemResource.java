package com.maoyou.springframework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
}
