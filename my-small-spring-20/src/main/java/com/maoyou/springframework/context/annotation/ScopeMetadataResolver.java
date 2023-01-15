package com.maoyou.springframework.context.annotation;

import com.maoyou.springframework.beans.factory.config.BeanDefinition;

public interface ScopeMetadataResolver {

	ScopeMetadata resolveScopeMetadata(BeanDefinition definition);

}
