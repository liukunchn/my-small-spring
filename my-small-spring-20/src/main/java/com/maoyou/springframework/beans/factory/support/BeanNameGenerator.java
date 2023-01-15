package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;

public interface BeanNameGenerator {

	String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}
