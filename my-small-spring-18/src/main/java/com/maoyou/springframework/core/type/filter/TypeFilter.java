package com.maoyou.springframework.core.type.filter;

import com.maoyou.springframework.core.type.classreading.MetadataReader;
import com.maoyou.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 * @ClassName TypeFilter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/19 15:59
 * @Version 1.0
 */
public interface TypeFilter {
    boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException;
}
