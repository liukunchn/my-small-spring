package com.maoyou.springframework.core.type.classreading;

import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.type.AnnotationMetadata;
import com.maoyou.springframework.core.type.ClassMetadata;

/**
 * @ClassName MetadataReader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/19 15:29
 * @Version 1.0
 */
public interface MetadataReader {
    Resource getResource();
    ClassMetadata getClassMetadata();
    AnnotationMetadata getAnnotationMetadata();
}
