package com.maoyou.springframework.beans.factory.annotation;

import com.maoyou.springframework.beans.BeanCreationException;
import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.PropertyValues;
import com.maoyou.springframework.beans.factory.BeanFactory;
import com.maoyou.springframework.beans.factory.BeanFactoryAware;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.maoyou.springframework.beans.factory.InjectionPoint;
import com.maoyou.springframework.beans.factory.config.DependencyDescriptor;
import com.maoyou.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.maoyou.springframework.core.annotation.AnnotationAttributes;
import com.maoyou.springframework.core.annotation.AnnotationUtils;
import com.maoyou.springframework.core.annotation.MergedAnnotation;
import com.maoyou.springframework.core.annotation.MergedAnnotations;
import com.maoyou.springframework.util.Assert;
import com.maoyou.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName AutowiredAnnotationBeanPostProcessor
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2022/1/19 11:08
 * @Version 1.0
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(4);

    private String requiredParameterName = "required";

    private boolean requiredParameterValue = true;

    public AutowiredAnnotationBeanPostProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
        this.autowiredAnnotationTypes.add(Value.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalArgumentException(
                    "AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = findAutowiringMetadata(beanName, bean.getClass(), pvs);
        try {
            metadata.inject(bean, beanName, pvs);
        }
        catch (BeanCreationException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
        return pvs;
    }

    private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        InjectionMetadata metadata = buildAutowiringMetadata(clazz);
        return metadata;
    }

    private InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        if (!AnnotationUtils.isCandidateClass(clazz, this.autowiredAnnotationTypes)) {
            return InjectionMetadata.EMPTY;
        }
        List<InjectionMetadata.InjectedElement> elements = new ArrayList<>();
        Class<?> targetClass = clazz;
        final List<InjectionMetadata.InjectedElement> currElements = new ArrayList<>();
        for (Field field : targetClass.getDeclaredFields()) {
            MergedAnnotation<?> ann = findAutowiredAnnotation(field);
            if (ann != null) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                boolean required = determineRequiredStatus(ann);
                currElements.add(new AutowiredFieldElement(field, required));
            }
        }
        elements.addAll(0, currElements);
        return InjectionMetadata.forElements(elements, clazz);
    }

    private MergedAnnotation<?> findAutowiredAnnotation(AccessibleObject ao) {
        MergedAnnotations annotations = MergedAnnotations.from(ao);
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            MergedAnnotation<?> annotation = annotations.get(type);
            if (annotation.isPresent()) {
                return annotation;
            }
         }
        return null;
    }

    private boolean determineRequiredStatus(MergedAnnotation<?> ann) {
        return determineRequiredStatus((AnnotationAttributes)
                ann.asMap(mergedAnnotation -> new AnnotationAttributes(mergedAnnotation.getType())));
    }

    @Deprecated
    protected boolean determineRequiredStatus(AnnotationAttributes ann) {
        return (!ann.containsKey(this.requiredParameterName) ||
                this.requiredParameterValue == ann.getBoolean(this.requiredParameterName));
    }


    private class AutowiredFieldElement extends InjectionMetadata.InjectedElement {
        private final boolean required;

        protected AutowiredFieldElement(Field field, boolean required) {
            super(field);
            this.required = required;
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
            Field field = (Field) this.member;
            Object value = resolveFieldValue(field, bean, beanName);
            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(bean, value);
            }
        }

        private Object resolveFieldValue(Field field, Object bean, String beanName) {
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
            desc.setContainingClass(bean.getClass());
            Set<String> autowiredBeanNames = new LinkedHashSet<>(1);
            Assert.state(beanFactory != null, "No BeanFactory available");
            Object value;
            try {
                value = beanFactory.resolveDependency(desc, beanName, autowiredBeanNames);
            }
            catch (BeansException ex) {
                throw new BeanCreationException("Unsatisfied dependency expressed through " + new InjectionPoint(field), ex.getMessage(), ex);
            }
            return value;
        }
    }
}
