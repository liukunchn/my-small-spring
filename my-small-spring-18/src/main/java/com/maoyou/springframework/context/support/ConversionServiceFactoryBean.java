package com.maoyou.springframework.context.support;

import com.maoyou.springframework.beans.factory.FactoryBean;
import com.maoyou.springframework.beans.factory.InitializingBean;
import com.maoyou.springframework.core.convert.ConversionService;
import com.maoyou.springframework.core.convert.converter.Converter;
import com.maoyou.springframework.core.convert.converter.ConverterFactory;
import com.maoyou.springframework.core.convert.converter.ConverterRegistry;
import com.maoyou.springframework.core.convert.converter.GenericConverter;
import com.maoyou.springframework.core.convert.support.DefaultConversionService;
import com.maoyou.springframework.core.convert.support.GenericConversionService;
import com.maoyou.springframework.lang.Nullable;

import java.util.Set;

public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

	@Nullable
	private Set<?> converters;

	@Nullable
	private GenericConversionService conversionService;

	public void setConverters(Set<?> converters) {
		this.converters = converters;
	}

	@Override
	public void afterPropertiesSet() {
		this.conversionService = new DefaultConversionService();
		ConverterRegistry registry = this.conversionService;
		if (converters != null) {
			for (Object converter : converters) {
				if (converter instanceof GenericConverter) {
					registry.addConverter((GenericConverter) converter);
				}
				else if (converter instanceof Converter<?, ?>) {
					registry.addConverter((Converter<?, ?>) converter);
				}
				else if (converter instanceof ConverterFactory<?, ?>) {
					registry.addConverterFactory((ConverterFactory<?, ?>) converter);
				}
				else {
					throw new IllegalArgumentException("Each converter object must implement one of the " +
							"Converter, ConverterFactory, or GenericConverter interfaces");
				}
			}
		}
	}

	// implementing FactoryBean

	@Override
	@Nullable
	public ConversionService getObject() {
		return this.conversionService;
	}

	@Override
	public Class<? extends ConversionService> getObjectType() {
		return GenericConversionService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
