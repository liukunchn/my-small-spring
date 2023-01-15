package com.maoyou.springframework.context.annotation;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.core.annotation.AnnotatedElementUtils;
import com.maoyou.springframework.core.annotation.AnnotationAttributes;
import com.maoyou.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;

/**
 * @ClassName AnnotationScopeMetadataResolver
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/21 23:46
 * @Version 1.0
 */
public class AnnotationScopeMetadataResolver implements ScopeMetadataResolver {
    private final ScopedProxyMode defaultProxyMode;
    protected Class<? extends Annotation> scopeAnnotationType = Scope.class;
    public AnnotationScopeMetadataResolver() {
        this.defaultProxyMode = ScopedProxyMode.NO;
    }
    @Override
    public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
        ScopeMetadata metadata = new ScopeMetadata();
        if (definition.getMetadata() != null) {
            BeanDefinition annDef =  definition;
            AnnotationAttributes attributes = AnnotationAttributes.fromMap(annDef.getMetadata().getAnnotationAttributes(scopeAnnotationType.getName(), false));
            if (attributes != null) {
                metadata.setScopeName(attributes.getString("value"));
                ScopedProxyMode proxyMode = attributes.getEnum("proxyMode");
                if (proxyMode == ScopedProxyMode.DEFAULT) {
                    proxyMode = this.defaultProxyMode;
                }
                metadata.setScopedProxyMode(proxyMode);
            }
        }
        return metadata;
    }
}
