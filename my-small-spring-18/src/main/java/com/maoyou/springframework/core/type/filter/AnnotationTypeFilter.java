package com.maoyou.springframework.core.type.filter;

import com.maoyou.springframework.core.annotation.AnnotationUtils;
import com.maoyou.springframework.core.type.AnnotationMetadata;
import com.maoyou.springframework.core.type.classreading.MetadataReader;
import com.maoyou.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;

/**
 * @ClassName AnnotationTypeFilter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/19 16:43
 * @Version 1.0
 */
public class AnnotationTypeFilter extends AbstractTypeHierarchyTraversingFilter {
    private final Class<? extends Annotation> annotationType;

    private final boolean considerMetaAnnotations;

    public AnnotationTypeFilter(Class<? extends Annotation> annotationType) {
        this(annotationType, true, false);
    }

    public AnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations) {
        this(annotationType, considerMetaAnnotations, false);
    }

    public AnnotationTypeFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations, boolean considerInterfaces) {
        super(annotationType.isAnnotationPresent(Inherited.class), considerInterfaces);
        this.annotationType = annotationType;
        this.considerMetaAnnotations = considerMetaAnnotations;
    }

    public Class<? extends Annotation> getAnnotationType() {
        return annotationType;
    }

    @Override
    protected boolean matchSelf(MetadataReader metadataReader) {
        AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
        return metadata.hasAnnotation(annotationType.getName())
                || (considerMetaAnnotations && metadata.hasMetaAnnotation(annotationType.getName()));
    }

    @Override
    protected boolean matchClassName(String className) {
        return false;
    }

    @Override
    protected Boolean matchSuperClassName(String superClassName) {
        return hasAnnotation(superClassName);
    }

    @Override
    protected Boolean matchInterface(String ifc) {
        return hasAnnotation(ifc);
    }

    private Boolean hasAnnotation(String typeName) {
        if (Object.class.getName().equals(typeName)) {
            return false;
        }
        else if (typeName.startsWith("java")) {
            if (!this.annotationType.getName().startsWith("java")) {
                // Standard Java types do not have non-standard annotations on them ->
                // skip any load attempt, in particular for Java language interfaces.
                return false;
            }
            try {
                Class<?> clazz = ClassUtils.forName(typeName, getClass().getClassLoader());
                return ((this.considerMetaAnnotations ? AnnotationUtils.getAnnotation(clazz, this.annotationType) :
                        clazz.getAnnotation(this.annotationType)) != null);
            }
            catch (Throwable ex) {
                // Class not regularly loadable - can't determine a match that way.
            }
        }
        return null;
    }
}
