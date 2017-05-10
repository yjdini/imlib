package com.utils.convert;

import com.utils.ConstJson;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Somnus`L on 2017/5/10.
 */
public class DateConvert implements Converter<DateTime, Long> {
    @Override
    public Long convert(DateTime source) {
        return source.getMillis();
    }
}
