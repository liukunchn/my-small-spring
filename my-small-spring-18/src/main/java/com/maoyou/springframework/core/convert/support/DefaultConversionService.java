package com.maoyou.springframework.core.convert.support;

import com.maoyou.springframework.core.convert.ConversionService;
import com.maoyou.springframework.core.convert.converter.ConverterRegistry;

/**
 * @ClassName DefaultConversionService
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/12/12 21:20
 * @Version 1.0
 */
public class DefaultConversionService extends GenericConversionService {
    private static volatile DefaultConversionService sharedInstance;

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static ConversionService getSharedInstance() {
        DefaultConversionService cs = sharedInstance;
        if (cs == null) {
            synchronized (DefaultConversionService.class) {
                cs = sharedInstance;
                if (cs == null) {
                    cs = new DefaultConversionService();
                    sharedInstance = cs;
                }
            }
        }
        return cs;
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
        converterRegistry.addConverter(new StringToBooleanConverter());

    }
}
