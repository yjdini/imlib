package com.ini.data.jpa;

import com.ini.data.entity.StatisticIncrement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface StatisticIncrementRepository extends JpaRepository<StatisticIncrement, Integer>, QueryByExampleExecutor<StatisticIncrement> {
    @Query("select max(time) from StatisticIncrement")
    Integer getLastTime();

    List<StatisticIncrement> findBySubIdAndTimeBetween(Integer subId, Integer min, Integer max);

    @Query("select count(*) from User where subId = ?1 and date_format(createTime,'%Y%m%d') = ?2")
    Integer getUserCount(Integer subId, String time);

    @Query("select count(*) from User where subId = ?1 and date_format(qualifyTime,'%Y%m%d') = ?2 " +
            " and type = 'm'")
    Integer getMasterCount(Integer subId, String time);

    @Query("select count(*) from Orders where subId = ?1 and date_format(createTime,'%Y%m%d') = ?2 " +
            " and result = 3")
    Integer getFinishOrderCount(Integer subId, String time);

    @Query("select count(*) from Orders where subId = ?1 and date_format(createTime,'%Y%m%d') = ?2")
    Integer getOrderCount(Integer subId, String s);

    @Query("select count(*) from Skill where subId = ?1 and date_format(createTime,'%Y%m%d') = ?2")
    Integer getSkillCount(Integer subId, String s);

    @Query("select count(*) from Apply where subId = ?1 and date_format(createTime,'%Y%m%d') = ?2")
    Integer getApplyCount(Integer subId, String s);



    @Query("select count(*) from Orders where subId = ?1 and date_format(createTime,'%Y%m%d') <= ?2")
    Integer getAllOrderCount(Integer subId, String s);

    @Query("select count(*) from Skill where subId = ?1 and date_format(createTime,'%Y%m%d') <= ?2")
    Integer getAllSkillCount(Integer subId, String s);

    @Query("select count(*) from User where subId = ?1 and date_format(qualifyTime,'%Y%m%d') <= ?2 " +
            " and type = 'm'")
    Integer getAllMasterCount(Integer subId, String s);

    @Query("select count(*) from User where subId = ?1 and date_format(createTime,'%Y%m%d') <= ?2")
    Integer getAllUserCount(Integer subId, String s);

    @Query("select count(*) from Orders where subId = ?1 and date_format(createTime,'%Y%m%d') <= ?2 " +
            " and result = 3")
    Integer getAllFinishOrderCount(Integer subId, String s);

    @Query("select count(*) from Apply where subId = ?1 and date_format(createTime,'%Y%m%d') <= ?2")
    Integer getAllApplyCount(Integer subId, String s);
}
