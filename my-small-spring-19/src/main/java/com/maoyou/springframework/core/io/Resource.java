package com.maoyou.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @ClassName Resource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 17:41
 * @Version 1.0
 */
public interface Resource extends InputStreamSource {
    URL getURL() throws IOException;
    Resource createRelative(String relativePath) throws IOException;
    File getFile() throws IOException;
}
