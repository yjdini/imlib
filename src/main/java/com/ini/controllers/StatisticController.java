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

import java.util.HashMap;
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
    public Map statistic(@RequestBody Map<String, Object> body, @PathVariable String type) {
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

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping("/order")
    public Map statistic(@RequestBody Map<String, Object> body) {
        Integer endDate = (Integer) body.get("endDate");//20170501
        Integer startDate = (Integer) body.get("startDate");
        Integer subId = sessionUtil.getSubId();
        if (startDate > endDate) {
            return ResultMap.error().setMessage("开始日期大于结束日期").getMap();
        } else if ((startDate + 10000) < endDate) {
            return ResultMap.error().setMessage("最多只能查询一年内的结果").getMap();
        }

        Map orderSums = statisticsService.getStatisticSum(startDate, endDate, "order", subId);
        Map orderIncrements = statisticsService.getStatisticIncrement(startDate, endDate, "order", subId);
        Map finishOrderSums = statisticsService.getStatisticSum(startDate, endDate, "finishOrder", subId);
        Map finishOrderIncrements = statisticsService.getStatisticIncrement(startDate, endDate, "finishOrder", subId);

        HashMap sums = new HashMap();
        HashMap increments = new HashMap();
        sums.put("order", orderSums);
        sums.put("finishOrder", finishOrderSums);
        increments.put("order", orderIncrements);
        increments.put("finishOrder", finishOrderIncrements);
        return ResultMap.ok().result("sums", sums).result("increments", increments).getMap();
    }

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping("/count")
    public Map count() {
        Integer subId = sessionUtil.getSubId();
        Map count = statisticsService.count(subId);
        return ResultMap.ok().result(count).getMap();
    }

    @RequestMapping("/rootcount/{subId}")
    public Map rootCount(@PathVariable Integer subId) {
        Map count = statisticsService.rootCount(subId);
        return ResultMap.ok().result(count).getMap();
    }

    @RequestMapping("/rootincre/{subId}")
    public Map rootIncre(@PathVariable Integer subId) {
        Map count = statisticsService.rootIncre(subId);
        return ResultMap.ok().result(count).getMap();
    }
}
