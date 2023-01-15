package com.maoyou.springframework.context.support;

import com.maoyou.springframework.beans.BeanInstantiationException;
import com.maoyou.springframework.beans.BeansException;
import com.maoyou.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.maoyou.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import com.maoyou.springframework.context.EnvironmentAware;
import com.maoyou.springframework.core.env.*;
import com.maoyou.springframework.util.StringValueResolver;

import java.io.IOException;

/**
 * @ClassName PropertySourcesPlaceholderConfigurer
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/23 17:18
 * @Version 1.0
 */
public class PropertySourcesPlaceholderConfigurer extends PlaceholderConfigurerSupport implements EnvironmentAware {
    public static final String LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME = "localProperties";
    public static final String ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME = "environmentProperties";

    private MutablePropertySources propertySources;
    private PropertySources appliedPropertySources;
    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (this.propertySources == null) {
            this.propertySources = new MutablePropertySources();
            if (this.environment != null) {
                this.propertySources.addLast(
                        new PropertySource<Environment>(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, this.environment) {
                            @Override
                            public Object getProperty(String name) {
                                return null;
                            }
                        }
                );
            }
            try {
                PropertySource<?> localPropertySource = new PropertiesPropertySource(LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, super.mergeProperties());
                if (super.localOverride) {
                    this.propertySources.addFirst(localPropertySource);
                } else {
                    this.propertySources.addLast(localPropertySource);
                }
            } catch (IOException e) {
                throw new BeanInstantiationException("Could not load properties", e);
            }
        }
        processProperties(beanFactory, new PropertySourcesPropertyResolver(this.propertySources));
        this.appliedPropertySources = this.propertySources;
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, PropertySourcesPropertyResolver propertyResolver) {
        StringValueResolver valueResolver = strVal -> {
            String resolved = (this.ignoreUnresolvablePlaceholders ?
                    propertyResolver.resolvePlaceholders(strVal) :
                    propertyResolver.resolveRequiredPlaceholders(strVal));
            if (trimValues) {
                resolved = resolved.trim();
            }
            return resolved.equals(nullValue) ? null : resolved;
        };
        doProcessProperties(beanFactoryToProcess, valueResolver);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
