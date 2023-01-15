package com.maoyou.springframework.core.type.classreading;

import aj.org.objectweb.asm.ClassReader;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.type.AnnotationMetadata;
import com.maoyou.springframework.core.type.ClassMetadata;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName SimpleMetadataReader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/19 15:33
 * @Version 1.0
 */
public class SimpleMetadataReader implements MetadataReader {
    private static final int PARSING_OPTIONS = ClassReader.SKIP_DEBUG
            | ClassReader.SKIP_CODE | ClassReader.SKIP_FRAMES;

    private final Resource resource;

    private final AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(Resource resource, ClassLoader classLoader) throws IOException {
        this.resource = resource;
        SimpleAnnotationMetadataReadingVisitor visitor = new SimpleAnnotationMetadataReadingVisitor(classLoader);
        getClassReader(resource).accept(visitor, PARSING_OPTIONS);
        this.annotationMetadata = visitor.getMetadata();
    }

    private static ClassReader getClassReader(Resource resource) throws IOException {
        try (InputStream is = resource.getInputStrem()) {
            try {
                return new ClassReader(is);
            }
            catch (IllegalArgumentException ex) {
                throw new IOException("ASM ClassReader failed to parse class file - " +
                        "probably due to a new Java class file version that isn't supported yet: " + resource, ex);
            }
        }
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return annotationMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return annotationMetadata;
    }
}
