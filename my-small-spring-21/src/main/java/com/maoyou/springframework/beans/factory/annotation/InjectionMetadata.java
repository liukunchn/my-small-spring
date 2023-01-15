package com.maoyou.springframework.beans.factory.annotation;

import com.maoyou.springframework.beans.PropertyValues;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

/**
 * @ClassName InjectionMetadata
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/19 11:14
 * @Version 1.0
 */
public class InjectionMetadata {
    private final Class<?> targetClass;

    private final Collection<InjectedElement> injectedElements;

    public static final InjectionMetadata EMPTY = new InjectionMetadata(Object.class, Collections.emptyList()) {
//        @Override
//        public void checkConfigMembers(RootBeanDefinition beanDefinition) {
//        }
        @Override
        public void inject(Object target, String beanName, PropertyValues pvs) {
        }
//        @Override
//        public void clear(@Nullable PropertyValues pvs) {
//        }
    };


    public InjectionMetadata(Class<?> targetClass, Collection<InjectedElement> injectedElements) {
        this.targetClass = targetClass;
        this.injectedElements = injectedElements;
    }

    public void inject(Object target, String beanName, PropertyValues pvs) throws Throwable {
        Collection<InjectedElement> elementsToIterate = this.injectedElements;
        if (!elementsToIterate.isEmpty()) {
            for (InjectedElement element : elementsToIterate) {
                element.inject(target, beanName, pvs);
            }
        }
    }

    public static InjectionMetadata forElements(Collection<InjectedElement> elements, Class<?> clazz) {
        return (elements.isEmpty() ? new InjectionMetadata(clazz, Collections.emptyList()) :
                new InjectionMetadata(clazz, elements));
    }



    public abstract static class InjectedElement {
        protected final Member member;

        protected final boolean isField;

        protected volatile Boolean skip;

        protected InjectedElement(Member member) {
            this.member = member;
            this.isField = member instanceof Field;
        }

        public Member getMember() {
            return this.member;
        }

        protected Class<?> getResourceType() {
            if (this.isField) {
                return ((Field) this.member).getType();
            } else {
                return ((Method) this.member).getParameterTypes()[0];
            }
        }

        protected void inject(Object target, String beanName, PropertyValues pvs) throws Throwable {
            //  由AutowiredFieldElement和AutowiredMethodElement两个子类实现
        }
    }
}
