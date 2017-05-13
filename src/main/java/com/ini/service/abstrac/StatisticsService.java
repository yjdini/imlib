package com.ini.service.abstrac;

import com.ini.data.entity.StatisticIncrement;
import com.ini.data.entity.StatisticSum;

import java.util.List;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/4/5.
 *
 */
public interface StatisticsService {
    Map getStatisticSum(Integer startDate, Integer endDate, String type, Integer subId);

    Map getStatisticIncrement(Integer startDate, Integer endDate, String type, Integer subId);

    void start();
}
