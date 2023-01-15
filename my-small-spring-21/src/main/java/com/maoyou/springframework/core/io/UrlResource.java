package com.maoyou.springframework.core.io;

import com.maoyou.springframework.util.ResourceUtils;
import com.maoyou.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ClassName UrlResource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/18 17:41
 * @Version 1.0
 */
public class UrlResource implements Resource {
    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStrem() throws IOException {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        }
        catch (IOException ex) {
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }

    @Override
    public URL getURL() throws IOException {
        return this.url;
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return new UrlResource(createRelativeURL(relativePath));
    }

    @Override
    public File getFile() throws IOException {
        URL url = getURL();
        return ResourceUtils.getFile(url, "");
    }

    private URL createRelativeURL(String relativePath) throws MalformedURLException {
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        // # can appear in filenames, java.net.URL should not treat it as a fragment
        relativePath = StringUtils.replace(relativePath, "#", "%23");
        // Use the URL constructor for applying the relative path as a URL spec
        return new URL(this.url, relativePath);
    }

    @Override
    public String toString() {
        return "UrlResource{" +
                "url=" + url +
                '}';
    }
}
