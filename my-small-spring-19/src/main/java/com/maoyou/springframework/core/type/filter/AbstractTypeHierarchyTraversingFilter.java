package com.maoyou.springframework.core.type.filter;

import com.maoyou.springframework.core.type.ClassMetadata;
import com.maoyou.springframework.core.type.classreading.MetadataReader;
import com.maoyou.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 * @ClassName AbstractTypeHierarchyTraversingFilter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/11/19 16:00
 * @Version 1.0
 */
public abstract class AbstractTypeHierarchyTraversingFilter implements TypeFilter {
    private final boolean considerInherited;

    private final boolean considerInterfaces;

    public AbstractTypeHierarchyTraversingFilter(boolean considerInherited, boolean considerInterfaces) {
        this.considerInherited = considerInherited;
        this.considerInterfaces = considerInterfaces;
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if (matchSelf(metadataReader)) {
            return true;
        }
        ClassMetadata metadata = metadataReader.getClassMetadata();
        if (matchClassName(metadata.getClassName())) {
            return true;
        }
        if (considerInherited) {
            String superClassName = metadata.getSuperClassName();
            if (superClassName != null) {
                Boolean superClassMatch = matchSuperClassName(superClassName);
                if (superClassMatch != null) {
                    if (superClassMatch.booleanValue()) {
                        return true;
                    }
                } else {
                    try {
                        if (match(superClassName, metadataReaderFactory)) {
                            return true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (considerInterfaces) {
            String[] interfaceNames = metadata.getInterfaceNames();
            for (String ifc : interfaceNames) {
                if (ifc != null) {
                    Boolean superClassMatch = matchInterface(ifc);
                    if (superClassMatch != null) {
                        if (superClassMatch.booleanValue()) {
                            return true;
                        }
                    } else {
                        try {
                            if (match(ifc, metadataReaderFactory)) {
                                return true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    protected boolean matchSelf(MetadataReader metadataReader) {
        return false;
    }

    protected boolean matchClassName(String className) {
        return false;
    }

    protected Boolean matchSuperClassName(String superClassName) {
        return null;
    }

    protected Boolean matchInterface(String ifc) {
        return null;
    }

    private boolean match(String className, MetadataReaderFactory metadataReaderFactory) throws IOException {
        return match(metadataReaderFactory.getMetadataReader(className), metadataReaderFactory);
    }
}
