package com.maoyou.springframework.beans.factory.xml;

import com.maoyou.springframework.beans.MutablePropertyValues;
import com.maoyou.springframework.beans.PropertyValue;
import com.maoyou.springframework.beans.factory.BeanDefinitionStoreException;
import com.maoyou.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.maoyou.springframework.beans.factory.config.BeanDefinition;
import com.maoyou.springframework.beans.factory.config.BeanReference;
import com.maoyou.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.maoyou.springframework.beans.factory.support.BeanDefinitionReader;
import com.maoyou.springframework.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

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
            Element bean = (Element) it.next();
            if (!"bean".equals(bean.getName())) continue;

            BeanDefinition bd = new BeanDefinition();
            bd.setBeanClassName(bean.attributeValue("class"));

            MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
            Iterator it2 = bean.elementIterator();
            while (it2.hasNext()) {
                Element e = (Element) it2.next();
                if ("property".equals(e.getName()) && e.attributeValue("name") != null && !"".equals(e.attributeValue("name"))) {
                    PropertyValue pv;
                    if (e.attributeValue("ref") != null && !"".equals(e.attributeValue("ref"))) {
                        pv = new PropertyValue(e.attributeValue("name"), new BeanReference(e.attributeValue("ref")));
                    } else if (e.attributeValue("value") != null && !"".equals(e.attributeValue("value"))) {
                        pv = new PropertyValue(e.attributeValue("name"), e.attributeValue("value"));
                    } else {
                        throw new RuntimeException();
                    }
                    mutablePropertyValues.addPropertyValue(pv);
                }
            }
            bd.setPropertyValues(mutablePropertyValues);

            getRegistry().registerBeanDefinition(bean.attributeValue("id"), bd);
        }
    }
}
