package com.ini.utils.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ini.utils.ConstJson;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Somnus`L on 2017/5/9.
 */
//@Component
public class ResultConvert implements Converter<ConstJson.Result, String> {

    @Override
    public String convert(ConstJson.Result source) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
