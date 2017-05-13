package com.ini.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/13.
 */
public class DateUtil {
    /**
     *
     * @param endDate 20160302
     * @param num
     * @return
     */
    public Integer getStartDateInt(Integer endDate, Integer num) {
        Integer year = endDate / 10000;
        Integer month = ((endDate % 10000) / 100) -1;
        Integer date = endDate % 100;


        Calendar end = Calendar.getInstance();
        end.set(year, month, date);
        end.set(Calendar.DATE, end.get(Calendar.DATE) - 1);

        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Integer start = Integer.valueOf(dft.format(end.getTime()));

        return start;
    }

    public Map<Integer,Object> getDateValueMap(Integer startDate, Integer endDate, Object initial) {


        return null;
    }
}
