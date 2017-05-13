package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.service.abstrac.StatisticsService;
import com.ini.utils.DateUtil;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/13.
 *
 */
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    @Autowired private DateUtil dateUtil;
    @Autowired private StatisticsService statisticsService;
    @Autowired private SessionUtil sessionUtil;

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping("/{type}")
    public Map userStatistic(@RequestBody Map<String, Object> body, @PathVariable String type) {
        Integer endDate = (Integer) body.get("endDate");//20170501
        Integer startDate = (Integer) body.get("startDate");
        Integer subId = sessionUtil.getSubId();
        if (startDate > endDate) {
            return ResultMap.error().setMessage("开始日期大于结束日期").getMap();
        } else if ((startDate + 10000) < endDate) {
            return ResultMap.error().setMessage("最多只能查询一年内的结果").getMap();
        }

        Map sums = statisticsService.getStatisticSum(startDate, endDate, type, subId);
        Map increments = statisticsService.getStatisticIncrement(startDate, endDate, type, subId);
        return ResultMap.ok().result("sums", sums).result("increments", increments).getMap();
    }
}
