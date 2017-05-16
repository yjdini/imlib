package com.ini.service.abstrac;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/4/5.
 *
 */
public interface StatisticsService {
    Map getStatisticSum(Integer startDate, Integer endDate, String type, Integer subId);

    Map getStatisticIncrement(Integer startDate, Integer endDate, String type, Integer subId);

    void start();


    Map count(Integer subId);

    Map rootCount(Integer subId);

    Map rootIncre(Integer subId);
}
