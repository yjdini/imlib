package com.ini.framework;

import com.ini.service.abstrac.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Somnus`L on 2017/5/14.
 *
 */
@WebListener
@Component
public class StartListener implements ServletContextListener {
    @Autowired private StatisticsService statisticsService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        statisticsService.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
