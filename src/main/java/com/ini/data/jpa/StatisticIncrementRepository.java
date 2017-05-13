package com.ini.data.jpa;

import com.ini.data.entity.StatisticIncrement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by Somnus`L on 2017/5/12.
 */
public interface StatisticIncrementRepository extends JpaRepository<StatisticIncrement, Integer>, QueryByExampleExecutor<StatisticIncrement> {

}
