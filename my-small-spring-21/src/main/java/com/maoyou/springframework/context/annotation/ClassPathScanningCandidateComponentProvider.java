package com.maoyou.springframework.context.annotation;

import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.core.io.ResourceLoader;
import com.maoyou.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.maoyou.springframework.core.io.support.ResourcePatternResolver;
import com.maoyou.springframework.core.type.AnnotationMetadata;
import com.maoyou.springframework.core.type.classreading.MetadataReader;
import com.maoyou.springframework.core.type.classreading.MetadataReaderFactory;
import com.maoyou.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import com.maoyou.springframework.core.type.filter.AnnotationTypeFilter;
import com.maoyou.springframework.core.type.filter.TypeFilter;
import com.maoyou.springframework.stereotype.Component;
import com.maoyou.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName ClassPathScanningCandidateComponentProvider
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/3 12:13
 * @Version 1.0
 */
public class ClassPathScanningCandidateComponentProvider {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private String resourcePattern = DEFAULT_RESOURCE_PATTERN;

    private ResourcePatternResolver resourcePatternResolver;

    private final List<TypeFilter> includeFilters = new ArrayList<>();

    private final List<TypeFilter> excludeFilters = new ArrayList<>();

    private MetadataReaderFactory metadataReaderFactory;

    protected void registerDefaultFilters() {
        this.includeFilters.add(new AnnotationTypeFilter(Component.class));
    }

    public MetadataReaderFactory getMetadataReaderFactory() {
        if (this.metadataReaderFactory == null) {
            this.metadataReaderFactory = new SimpleMetadataReaderFactory();
        }
        return metadataReaderFactory;
    }

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        return scanCandidateComponents(basePackage);
    }

    private Set<BeanDefinition> scanCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + this.resourcePattern;
            Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {
                MetadataReader metadataReader = getMetadataReaderFactory().getMetadataReader(resource);
                if (isCandidateComponent(metadataReader)) {
                    BeanDefinition bd = new BeanDefinition(metadataReader);
                    if (isCandidateComponent(bd)) {
                        candidates.add(bd);
                    }
                }
            }
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", e);
        }
        return candidates;
    }

    private boolean isCandidateComponent(BeanDefinition bd) {
        AnnotationMetadata metadata = bd.getMetadata();
        return metadata.isIndependent() && (metadata.isConcrete());
    }

    private boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        for (TypeFilter tf : this.excludeFilters) {
            if (tf.match(metadataReader, getMetadataReaderFactory())) {
                return false;
            }
        }
        for (TypeFilter tf : this.includeFilters) {
            if (tf.match(metadataReader, getMetadataReaderFactory())) {
                return isConditionMatch(metadataReader);
            }
        }
        return false;
    }

    private boolean isConditionMatch(MetadataReader metadataReader) {
        // TODO 处理condition
        return true;
    }

    protected String resolveBasePackage(String basePackage) {
        // TODO 支持对包含占位符的basePackage进行解析，使用了基于emvironment的解析器
        return ClassUtils.convertClassNameToResourcePath(basePackage);
    }

    public void setResourcePattern(String resourcePattern) {
        this.resourcePattern = resourcePattern;
    }

    public void setResourceLoader( ResourceLoader resourceLoader) {
        this.resourcePatternResolver = getResourcePatternResolver(resourceLoader);
    }

    private ResourcePatternResolver getResourcePatternResolver(ResourceLoader resourceLoader) {
        if (resourceLoader instanceof ResourcePatternResolver) {
            return (ResourcePatternResolver) resourceLoader;
        }
        else if (resourceLoader != null) {
            return new PathMatchingResourcePatternResolver(resourceLoader);
        }
        else {
            return new PathMatchingResourcePatternResolver();
        }
    }

    private ResourcePatternResolver getResourcePatternResolver() {
        if (this.resourcePatternResolver == null) {
            this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return this.resourcePatternResolver;
    }
}
