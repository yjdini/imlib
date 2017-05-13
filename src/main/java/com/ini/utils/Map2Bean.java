package com.ini.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;

import java.util.Date;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public class Map2Bean {
    public static <T> T convert(Map<String, Object> map, T bean, boolean filterBlank) {
        if (!filterBlank)
            return convert(map, bean);
        try {
            DateConverter dateConvert = new DateConverter();//写一个日期转换器
            String[] patterns = { "yyyyMMdd", "yyyy-MM-dd" };//限定日期的格式字符串数组
            dateConvert.setPatterns(patterns);
            BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
            IntegerConverter integerConverter = new IntegerConverter(null);
            ConvertUtils.register(dateConvert, Date.class);
            ConvertUtils.register(bigDecimalConverter, Date.class);
            ConvertUtils.register(integerConverter, Date.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value.equals("")) {
                    continue;
                }
                BeanUtils.setProperty(bean, entry.getKey(), value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    public static <T> T convert(Map<String, Object> map, T bean) {
        try {
            DateConverter dateConvert = new DateConverter();//写一个日期转换器
            String[] patterns = { "yyyyMMdd", "yyyy-MM-dd" };//限定日期的格式字符串数组
            dateConvert.setPatterns(patterns);
            BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
            IntegerConverter integerConverter = new IntegerConverter(null);
            ConvertUtils.register(dateConvert, Date.class);
            ConvertUtils.register(bigDecimalConverter, Date.class);
            ConvertUtils.register(integerConverter, Date.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                BeanUtils.setProperty(bean, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

}
