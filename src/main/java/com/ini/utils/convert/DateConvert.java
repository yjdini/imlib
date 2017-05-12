package com.ini.utils.convert;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Created by Somnus`L on 2017/5/10.
 */

public class DateConvert implements Converter<Date, Long> {
    @Override
    public Long convert(Date source) {
        return source.getTime();
    }
}
