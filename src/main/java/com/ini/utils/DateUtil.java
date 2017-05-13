package com.ini.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
        end.set(Calendar.DATE, end.get(Calendar.DATE) - num);

        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Integer start = Integer.valueOf(dft.format(end.getTime()));

        return start;
    }

    public Calendar parseDate(Integer dateInteger) {
        Integer year = dateInteger / 10000;
        Integer month = ((dateInteger % 10000) / 100) -1;
        Integer date = dateInteger % 100;

        Calendar re = Calendar.getInstance();
        re.set(year, month, date);
        return re;
    }

    public Integer formatDate(Calendar date) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        return Integer.valueOf(dft.format(date.getTime()));
    }

    public Map<Integer,Object> getDateValueMap(Integer startDate, Integer endDate, Object initial) {
        Calendar start = parseDate(startDate);
        HashMap<Integer,Object> re = new HashMap<Integer,Object>();
        while (!startDate.equals(endDate)) {
            re.put(startDate, initial);
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
            startDate = formatDate(start);
        }
        re.put(startDate, initial);
        return re;
    }
}
