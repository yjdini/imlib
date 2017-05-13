package com.ini.data.jpa;

import com.ini.data.entity.StatisticSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 */
public interface StatisticSumRepository extends JpaRepository<StatisticSum, Integer>, QueryByExampleExecutor<StatisticSum> {

    List<StatisticSum> findByTimeBetween(Integer min, Integer max);
}
