package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.data.entity.Admin;
import com.ini.data.entity.OpenSub;
import com.ini.data.entity.Sub;
import com.ini.data.entity.User;
import com.ini.data.jpa.AdminRepository;
import com.ini.data.jpa.OpenSubRepository;
import com.ini.data.jpa.SubRepository;
import com.ini.service.abstrac.AdminService;
import com.ini.service.abstrac.RootService;
import com.ini.service.abstrac.StatisticsService;
import com.ini.utils.MD5Util;
import com.ini.utils.Map2Bean;
import com.ini.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/14.
 *
 */

@RestController
@RequestMapping("/api/root")
public class RootController {
    @Autowired private RootService rootService;
    @Autowired private OpenSubRepository openSubRepository;
    @Autowired private SubRepository subRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private StatisticsService statisticsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map login(@RequestBody Map<String, Object> body)
    {
        String name = (String) body.get("name");
        String password = (String) body.get("password");
        return rootService.login(name, password);
    }

    @RequestMapping(value = "/sub/list")
    public Map getSubList()
    {

        return rootService.getSubList();
    }


    @RequestMapping(value = "/sys/info")
    public Map getSystemInfo()
    {
        return rootService.getSystemInfo();
    }


    @RequestMapping(value = "/sub/info/{subId}")
    public Map getSubInfo(@PathVariable Integer subId)
    {
        Admin admin = adminRepository.findBySubId(subId);
        return ResultMap.ok().result(admin).getMap();
    }

    @RequestMapping(value = "/closesub/{subId}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map closeSub(@PathVariable Integer subId, @RequestBody Map<String, Object> body)
    {
        String closeReason = (String) body.get("closeReason");
        String password = (String) body.get("password");
        return rootService.closeSub(subId, closeReason, password);
    }

    @RequestMapping(value = "/startsub/{subId}")
    public Map startSub(@PathVariable Integer subId)
    {
        return rootService.startSub(subId);
    }

    @RequestMapping(value = "/opensub/list")
    public Map getOpenSubList(@RequestBody Map<String, Object> body)
    {
        OpenSub openSub = Map2Bean.convert(body, new OpenSub(), true);
        Integer currentPage = new Integer((String)body.get("currentPage"));
        return rootService.getOpenSubList(openSub, currentPage);
    }

    @RequestMapping(value = "/master/list")
    public Map getMasterList(@RequestBody Map<String, Object> body)
    {
        Integer currentPage = new Integer((String)body.get("currentPage"));
        Integer subId = new Integer((String)body.get("subId"));
        return rootService.getMasterList(subId, currentPage);
    }

    @RequestMapping(value = "/user/list")
    public Map getUserList(@RequestBody Map<String, Object> body)
    {
        Integer currentPage = new Integer((String)body.get("currentPage"));
        Integer subId = new Integer((String)body.get("subId"));
        return rootService.getUserList(subId, currentPage);
    }

    @RequestMapping("/statistic/sum")
    public Map statisticSum(@RequestBody Map<String, Object> body) {
        Integer endDate = (Integer) body.get("endDate");//20170501
        Integer startDate = (Integer) body.get("startDate");
        Integer subId = (Integer) body.get("subId");

        String types = (String) body.get("dataId");

        if (startDate > endDate) {
            return ResultMap.error().setMessage("开始日期大于结束日期").getMap();
        } else if ((startDate + 10000) < endDate) {
            return ResultMap.error().setMessage("最多只能查询一年内的结果").getMap();
        }
        String[] typess = types.split(",");
        List result = new ArrayList();

        for (String t : typess) {
            Map sums = statisticsService.getStatisticSum(startDate, endDate, t, subId);
            sums.put("name", t);
            result.add(sums);
        }
        return ResultMap.ok().result(result).getMap();
    }

    @RequestMapping("/statistic/incre")
    public Map statisticIncre(@RequestBody Map<String, Object> body) {
        Integer endDate = (Integer) body.get("endDate");//20170501
        Integer startDate = (Integer) body.get("startDate");
        Integer subId = (Integer) body.get("subId");

        String types = (String) body.get("dataId");

        if (startDate > endDate) {
            return ResultMap.error().setMessage("开始日期大于结束日期").getMap();
        } else if ((startDate + 10000) < endDate) {
            return ResultMap.error().setMessage("最多只能查询一年内的结果").getMap();
        }
        String[] typess = types.split(",");
        List result = new ArrayList();

        for (String t : typess) {
            Map sums = statisticsService.getStatisticIncrement(startDate, endDate, t, subId);
            sums.put("name", t);
            result.add(sums);
        }
        return ResultMap.ok().result(result).getMap();
    }



    @Transactional
    @RequestMapping(value = "/approveopen/{openSubId}")
    public Map approve(@PathVariable Integer openSubId)
    {
        OpenSub openSub = openSubRepository.findOne(openSubId);
        Sub sub = new Sub(true);
        sub.setToken(MD5Util.MD5("sub" + openSub.getOpenSubId()));
        subRepository.save(sub);

        Admin admin = new Admin(true);
        admin.setEmail(openSub.getEmail());
        admin.setMpName(openSub.getMpName());
        admin.setMpNum(openSub.getMpNum());
        admin.setSchoolName(openSub.getSchoolName());
        admin.setWechat(openSub.getWechat());
        admin.setPassword(openSub.getEmail());
        admin.setSubId(sub.getSubId());
        admin.setToken(sub.getToken());
        adminRepository.save(admin);

        openSub.setResult(1);
        openSub.setStatus(0);//处理后默认删除
        openSubRepository.save(openSub);

        return ResultMap.ok().getMap();
    }

    @RequestMapping(value = "/rejectopen/{openSubId}")
    public Map reject(@PathVariable Integer openSubId)
    {
        OpenSub openSub = openSubRepository.findOne(openSubId);
        openSub.setResult(2);
        openSub.setStatus(0);//处理后默认删除
        openSubRepository.save(openSub);
        return ResultMap.ok().getMap();
    }


}
