package com.ini.service.implement;

import com.ini.service.StatisticsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class StatisticsServiceImpl implements StatisticsService {
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void test() {

    }
}
