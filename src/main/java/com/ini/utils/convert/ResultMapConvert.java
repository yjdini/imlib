package com.ini.utils.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ini.utils.ResultMap;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */
//@Component
public class ResultMapConvert implements Converter<ResultMap, String> {
    @Override
    public String convert(ResultMap source) {
        try {
            Map map = source.getMap();
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
