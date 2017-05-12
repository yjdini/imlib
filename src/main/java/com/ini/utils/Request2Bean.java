package com.ini.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;  

import org.apache.commons.beanutils.BeanUtils;  
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;  
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;


public class Request2Bean {
	private static DateFormat  format1 = new SimpleDateFormat("yyyy-MM-dd");

	public static <T> T Convert(HttpServletRequest request, T bean) {
        try {
        	request.setCharacterEncoding("utf-8");
            Enumeration<?> parameterNames = request.getParameterNames();

            DateConverter convert = new DateConverter();//写一个日期转换器
            String[] patterns = { "yyyyMMdd", "yyyy-MM-dd" };//限定日期的格式字符串数组
            convert.setPatterns(patterns);

            ConvertUtils.register(convert, Date.class);
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

            BigDecimalConverter converter = new BigDecimalConverter(null);
            BeanUtilsBean.getInstance().getConvertUtils().register(converter, BigDecimal.class);

            IntegerConverter converter2 = new IntegerConverter(null);
            BeanUtilsBean.getInstance().getConvertUtils().register(converter2, Integer.class);

            while (parameterNames.hasMoreElements()) {
                String name = (String) parameterNames.nextElement();
                String value = request.getParameter(name);
				BeanUtils.setProperty(bean, name, value);//使用BeanUtils来设置对象属性的值
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }


}