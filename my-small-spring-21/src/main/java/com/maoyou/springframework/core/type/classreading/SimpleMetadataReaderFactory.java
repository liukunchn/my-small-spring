package com.maoyou.springframework.core.type.classreading;

import com.maoyou.springframework.core.io.DefaultResourceLoader;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.io.ResourceLoader;
import com.maoyou.springframework.util.ClassUtils;

import java.io.IOException;

/**
 * @ClassName SimpleMetadataReaderFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/19 15:41
 * @Version 1.0
 */
public class SimpleMetadataReaderFactory implements MetadataReaderFactory {
    private ResourceLoader resourceLoader;

    public SimpleMetadataReaderFactory() {
        this.resourceLoader = new DefaultResourceLoader();
    }

    public SimpleMetadataReaderFactory(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader != null ? resourceLoader : new DefaultResourceLoader();
    }

    public SimpleMetadataReaderFactory(ClassLoader classLoader) {
        this.resourceLoader = new DefaultResourceLoader(classLoader);
    }

    @Override
    public MetadataReader getMetadataReader(String className) throws IOException {
        String resourcePath = ResourceLoader.CLASSPATH_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(className) + ClassUtils.CLASS_FILE_SUFFIX;
        Resource resource = resourceLoader.getResource(resourcePath);
        return getMetadataReader(resource);
    }

    @Override
    public MetadataReader getMetadataReader(Resource resource) throws IOException {
        return new SimpleMetadataReader(resource, resourceLoader.getClassLoader());
    }
}
