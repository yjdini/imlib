package com.ini.service;

import com.ini.data.entity.StatisticIncrement;
import com.ini.data.entity.StatisticSum;
import com.ini.data.jpa.StatisticIncrementRepository;
import com.ini.data.jpa.StatisticSumRepository;
import com.ini.service.abstrac.StatisticsService;
import com.ini.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired private DateUtil dateUtil;
    @Autowired private StatisticIncrementRepository incrementRepository;
    @Autowired private StatisticSumRepository sumRepository;

    @Override
    public Map getStatisticSum(Integer startDate, Integer endDate, String type) {
        List<StatisticSum> sums = sumRepository.findByTimeBetween(startDate, endDate);
        Integer initial = 0;
        Map resultMap = dateUtil.getDateValueMap(startDate, endDate, initial);
        fillSum(sums, resultMap, type);
        return resultMap;
    }

    @Override
    public Map getStatisticIncrement(Integer startDate, Integer endDate, String type) {
        List<StatisticIncrement> increments = incrementRepository.findByTimeBetween(startDate, endDate);
        Integer initial = 0;
        Map resultMap = dateUtil.getDateValueMap(startDate, endDate, initial);
        fillIncrement(increments, resultMap, type);
        return resultMap;
    }



    private void fillSum(List<StatisticSum> sums, Map resultMap, String type) {
        if ("user".equals(type)) {
            for (StatisticSum sum : sums) {
                resultMap.put(sum.getTime(), sum.getUser());
            }
        } else if ("skill".equals(type)) {
            for (StatisticSum sum : sums) {
                resultMap.put(sum.getTime(), sum.getSkill());
            }
        } else if ("order".equals(type)) {
            for (StatisticSum sum : sums) {
                resultMap.put(sum.getTime(), sum.getOrders());
            }
        } else if ("finishOrder".equals(type)) {
            for (StatisticSum sum : sums) {
                resultMap.put(sum.getTime(), sum.getFinishOrder());
            }
        } else if ("master".equals(type)) {
            for (StatisticSum sum : sums) {
                resultMap.put(sum.getTime(), sum.getMaster());
            }
        }
    }


    private void fillIncrement(List<StatisticIncrement> increments, Map resultMap, String type) {
        if ("user".equals(type)) {
            for (StatisticIncrement increment : increments) {
                resultMap.put(increment.getTime(), increment.getUser());
            }
        } else if ("skill".equals(type)) {
            for (StatisticIncrement increment : increments) {
                resultMap.put(increment.getTime(), increment.getSkill());
            }
        } else if ("order".equals(type)) {
            for (StatisticIncrement increment : increments) {
                resultMap.put(increment.getTime(), increment.getOrders());
            }
        } else if ("finishOrder".equals(type)) {
            for (StatisticIncrement increment : increments) {
                resultMap.put(increment.getTime(), increment.getFinishOrder());
            }
        } else if ("master".equals(type)) {
            for (StatisticIncrement increment : increments) {
                resultMap.put(increment.getTime(), increment.getMaster());
            }
        } else if ("request".equals(type)) {
            for (StatisticIncrement increment : increments) {
                resultMap.put(increment.getTime(), increment.getRequest());
            }
        }
    }

}
