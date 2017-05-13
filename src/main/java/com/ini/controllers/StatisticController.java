package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.data.entity.Apply;
import com.ini.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Authentication(value = AuthenticationType.Admin)
    @RequestMapping("/user")
    public Map user(@RequestBody Map<String, Object> body) {
        Integer endDate = (Integer) body.get("endDate");
        Integer num = (Integer) body.get("num");
        Integer startDate = dateUtil.getStartDateInt(endDate, num);

        Object initial = 0;
        Map<Integer, Object> sum = dateUtil.getDateValueMap(startDate, endDate, initial);
//        List<>
        return null;
    }


}
