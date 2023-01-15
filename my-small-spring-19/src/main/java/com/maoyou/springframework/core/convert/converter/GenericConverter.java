package com.maoyou.springframework.core.convert.converter;

import com.maoyou.springframework.core.convert.TypeDescriptor;
import com.maoyou.springframework.lang.Nullable;
import com.maoyou.springframework.util.Assert;

import java.util.Objects;
import java.util.Set;

/**
 * @ClassName GenericConverter
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/10 13:44
 * @Version 1.0
 */
public interface GenericConverter {
    @Nullable
    Set<ConvertiblePair> getConvertibleTypes();

    @Nullable
    Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType);

    final class ConvertiblePair {
        private final Class<?> sourceType;
        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "Source Type must not be null");
            Assert.notNull(targetType, "Target Type must not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public Class<?> getTargetType() {
            return targetType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConvertiblePair that = (ConvertiblePair) o;
            return Objects.equals(sourceType, that.sourceType) &&
                    Objects.equals(targetType, that.targetType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceType, targetType);
        }

        @Override
        public String toString() {
            return "ConvertiblePair{" +
                    "sourceType=" + sourceType +
                    ", targetType=" + targetType +
                    '}';
        }
    }

}
