package com.maoyou.springframework.beans.factory.xml;

import com.maoyou.springframework.beans.MutablePropertyValues;
import com.maoyou.springframework.beans.PropertyValue;
import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanReference;
import com.maoyou.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.maoyou.springframework.beans.factory.support.BeanDefinitionReader;
import com.maoyou.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.maoyou.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import com.maoyou.springframework.core.io.Resource;
import com.maoyou.springframework.util.ClassUtils;
import com.maoyou.springframework.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName XmlBeanDefinitionReader
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 12:33
 * @Version 1.0
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader implements BeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        try (InputStream inputStrem = resource.getInputStrem()) {
            doLoadBeanDefinitions(inputStrem);
        } catch (IOException e) {
            throw new BeanDefinitionStoreException(
                    "IOException parsing XML document from " + resource, e);
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException(
                    "DocumentException parsing XML document from " + resource, e);
        } catch (Throwable e) {
            throw new BeanDefinitionStoreException(
                    "Unexpected exception  parsing XML document from " + resource, e);
        }
    }

    private void doLoadBeanDefinitions(InputStream inputStrem) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStrem);
        Element beans = document.getRootElement();
        Iterator it = beans.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            // 处理component-scan标签
            if ("component-scan".equals(element.getName())) {
                String basePackge = element.attributeValue("base-package");
                if (!StringUtils.hasLength(basePackge)) {
                    throw new IllegalArgumentException("The value of base-package attribute can not be empty or null");
                }
                String[] basePackages = basePackge.split(",");
//                ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.getRegistry(), this.getResourceLoader());
                ClassPathBeanDefinitionScanner scanner = configureScanner(element);
                Set<BeanDefinition> beanDefinitions = scanner.doScan(basePackages);

            }
            // 处理bean标签
            if ("bean".equals(element.getName())) {
                BeanDefinition bd = new BeanDefinition();
                bd.setBeanClassName(element.attributeValue("class"));
                if (StringUtils.hasLength(element.attributeValue("init-method"))) {
                    bd.setInitMethodName(element.attributeValue("init-method"));
                }
                if (StringUtils.hasLength(element.attributeValue("destroy-method"))) {
                    bd.setDestroyMethodName(element.attributeValue("destroy-method"));
                }
                if (StringUtils.hasLength(element.attributeValue("scope"))) {
                    bd.setScope(element.attributeValue("scope"));
                }

                MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
                Iterator it2 = element.elementIterator();
                while (it2.hasNext()) {
                    Element e = (Element) it2.next();
                    if ("property".equals(e.getName()) && e.attributeValue("name") != null && !"".equals(e.attributeValue("name"))) {
                        PropertyValue pv;
                        if (e.attributeValue("ref") != null && !"".equals(e.attributeValue("ref"))) {
                            pv = new PropertyValue(e.attributeValue("name"), new BeanReference(e.attributeValue("ref")));
                        } else if (e.attributeValue("value") != null && !"".equals(e.attributeValue("value"))) {
                            String value = e.attributeValue("value");
                            pv = new PropertyValue(e.attributeValue("name"), value);
                        } else {
                            Iterator it3 = e.elementIterator();
                            if (it3.hasNext()) {
                                Element collection = (Element) it3.next();
                                if ("set".equals(collection.getName())) {
                                    HashSet<Object> set = new HashSet<>();
                                    Iterator it4 = collection.elementIterator();
                                    while (it4.hasNext()) {
                                        Element valueOrBean = (Element) it4.next();
                                        if ("value".equals(valueOrBean.getName())) {
                                            set.add(valueOrBean.getText());
                                        } else if ("ref".equals(valueOrBean.getName())) {
                                            set.add(new BeanReference(valueOrBean.attributeValue("bean")));
                                        }
                                    }
                                    pv = new PropertyValue(e.attributeValue("name"), set);
                                } else {
                                    throw new RuntimeException("暂不支持除了set之外的集合");
                                }
                            } else {
                                throw new RuntimeException("未指定属性值");
                            }
                        }
                        mutablePropertyValues.addPropertyValue(pv);
                    }
                }
                bd.setPropertyValues(mutablePropertyValues);
                // 保存Class所有属性的setter方法
                doSaveDescriptors(bd);
                getRegistry().registerBeanDefinition(element.attributeValue("id") != null ? element.attributeValue("id") : bd.getBeanClassName(), bd);
            }

        }
    }

    protected ClassPathBeanDefinitionScanner configureScanner(Element element) {
        return new ClassPathBeanDefinitionScanner(this.getRegistry(), true,
                null, this.getResourceLoader());
    }

    private void doSaveDescriptors(BeanDefinition bd) {
        try {
            bd.resolveBeanClass(ClassUtils.getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?> beanClass = bd.getBeanClass();
        Class current = beanClass;
        while (current != null) {
            Field[] fields = current.getDeclaredFields();
            Method[] methods = current.getDeclaredMethods();
            for (Field field : fields) {
                for (Method method : methods) {
                    String fieldName = field.getName();
                    String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    if (setterName.equals(method.getName())) {
                        bd.getDescriptors().put(fieldName, method);
                    }
                }
            }
            current = current.getSuperclass();
        }
    }
}
