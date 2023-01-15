package com.maoyou.springframework.core.type.classreading;

import com.maoyou.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @ClassName MetadataReaderFactory
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/19 15:40
 * @Version 1.0
 */
public interface MetadataReaderFactory {
    MetadataReader getMetadataReader(String className) throws IOException;
    MetadataReader getMetadataReader(Resource resource) throws IOException;
}
