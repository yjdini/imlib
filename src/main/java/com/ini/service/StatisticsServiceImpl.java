package com.ini.service;

import com.ini.data.entity.StatisticIncrement;
import com.ini.data.entity.StatisticSum;
import com.ini.data.jpa.*;
import com.ini.service.abstrac.StatisticsService;
import com.ini.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired private DateUtil dateUtil;
    @Autowired private StatisticIncrementRepository incrementRepository;
    @Autowired private StatisticSumRepository sumRepository;
    @Autowired private SubRepository subRepository;
    @Autowired private OrdersRepository ordersRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ApplyRepository applyRepository;

    @Override
    public Map getStatisticSum(Integer startDate, Integer endDate, String type, Integer subId) {
        List<StatisticSum> sums = sumRepository.findBySubIdAndTimeBetween(subId, startDate, endDate);
        Integer initial = 0;
        Map result = dateUtil.getDateValueMap(startDate, endDate, initial);
        Set timesFormat = dateUtil.getDateSet(startDate, endDate, "yyyy/MM/dd");
        fillSum(sums, result, type);
        Collection values = result.values();

        Map map = new HashMap();
        map.put("date", timesFormat);
        map.put("value", values);
        return map;
    }

    @Override
    public Map getStatisticIncrement(Integer startDate, Integer endDate, String type, Integer subId) {
        List<StatisticIncrement> increments = incrementRepository.findBySubIdAndTimeBetween(subId, startDate, endDate);
        Integer initial = 0;
        Map result = dateUtil.getDateValueMap(startDate, endDate, initial);
        Set timesFormat = dateUtil.getDateSet(startDate, endDate, "yyyy/MM/dd");
        fillIncrement(increments, result, type);
        Collection values = result.values();

        Map map = new HashMap();
        map.put("date", timesFormat);
        map.put("value", values);
        return map;
    }

    private static Timer timer = null;

    @Override
    public void start() {
        if(timer != null){
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    startStatistics();
                } catch (Exception e) {
                    e.printStackTrace();
                    start();
                }
            }

        },10000,1000*60*60*24);
    }

    @Override
    public Map count(Integer subId) {
        Integer orderNum = ordersRepository.countBySubId(subId);
        Integer userNum = userRepository.countBySubId(subId);
        Integer skillNum = skillRepository.countBySubId(subId);
        Integer masterNum = userRepository.countBySubIdAndType(subId, "m");
        Integer finishOrderNum = ordersRepository.countBySubIdAndResult(subId, 3);
        Integer applyNum = applyRepository.countBySubId(subId);

        HashMap map = new HashMap();
        map.put("order", orderNum);
        map.put("user", userNum);
        map.put("skill", skillNum);
        map.put("master", masterNum);
        map.put("finishOrder", finishOrderNum);
        map.put("apply", applyNum);
        return map;
    }

    private void startStatistics() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
        Integer time = dateUtil.formatDate(now);//要添加统计的日期：yyyymmdd

        Integer lastTime = incrementRepository.getLastTime();//最后一次统计的日期

        List<Integer> subIds = subRepository.getSubIds();
        if (lastTime == null) {//save last day's statistics
            addStatistics(time, subIds);
        } else {
            Set set = dateUtil.getDateSet(lastTime, time);
            set.remove(lastTime);
            for (Object aSet : set) {
                addStatistics((Integer) aSet, subIds);
            }
        }
    }

    private void addStatistics(Integer time, List<Integer> subIds) {
        StatisticIncrement increment = new StatisticIncrement(true);
        StatisticSum sum = new StatisticSum(true);
        increment.setTime(time);
        sum.setTime(time);

        for (Integer subId : subIds) {
            Integer orderNum = incrementRepository.getOrderCount(subId, time.toString());
            Integer userNum = incrementRepository.getUserCount(subId, time.toString());
            Integer skillNum = incrementRepository.getSkillCount(subId, time.toString());
            Integer masterNum = incrementRepository.getMasterCount(subId, time.toString());
            Integer finishOrderNum = incrementRepository.getFinishOrderCount(subId, time.toString());
            Integer applyNum = incrementRepository.getApplyCount(subId, time.toString());

            increment.setSubId(subId);
            increment.setOrders(orderNum);
            increment.setUser(userNum);
            increment.setSkill(skillNum);
            increment.setMaster(masterNum);
            increment.setFinishOrder(finishOrderNum);
            increment.setApply(applyNum);
            incrementRepository.save(increment);
        }

        for (Integer subId : subIds) {
            Integer orderNum = incrementRepository.getAllOrderCount(subId, time.toString());
            Integer userNum = incrementRepository.getAllUserCount(subId, time.toString());
            Integer skillNum = incrementRepository.getAllSkillCount(subId, time.toString());
            Integer masterNum = incrementRepository.getAllMasterCount(subId, time.toString());
            Integer finishOrderNum = incrementRepository.getAllFinishOrderCount(subId, time.toString());
            Integer applyNum = incrementRepository.getAllApplyCount(subId, time.toString());


            sum.setSubId(subId);
            sum.setOrders(orderNum);
            sum.setUser(userNum);
            sum.setSkill(skillNum);
            sum.setMaster(masterNum);
            sum.setFinishOrder(finishOrderNum);
            sum.setApply(applyNum);
            sumRepository.save(sum);
        }
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
