package com.maoyou.springframework.core.io.support;

import com.maoyou.springframework.core.io.InputStreamSource;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @ClassName EncodedResource
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 16:21
 * @Version 1.0
 */
public class EncodedResource implements InputStreamSource {
    private final Resource resource;
    private final String encoding;

    public EncodedResource(Resource resource) {
        this(resource, null);
    }

    public EncodedResource(Resource resource, String encoding) {
        Assert.notNull(resource, "Resource must not be null");
        this.resource = resource;
        this.encoding = encoding;
    }

    public Resource getResource() {
        return resource;
    }

    public String getEncoding() {
        return encoding;
    }

    @Override
    public InputStream getInputStrem() throws IOException {
        return resource.getInputStrem();
    }

    public boolean requiresReader() {
        return (this.encoding != null);
    }

    public Reader getReader() throws IOException {
        if (encoding != null) {
            return new InputStreamReader(resource.getInputStrem(), encoding);
        }
        return new InputStreamReader(resource.getInputStrem());
    }
}
