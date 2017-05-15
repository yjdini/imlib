package com.ini.data.jpa;

import com.ini.data.entity.Apply;
import com.ini.data.schema.ApplyUserSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface ApplyRepository extends JpaRepository<Apply, Integer>, QueryByExampleExecutor<Apply> {

    List<Apply> findByUserId(Integer userId);

    @Query("select new com.ini.data.schema.ApplyUserSet(a,u) from Apply a, User u where " +
            " u.applyId = a.applyId and a.result = ?1 and u.subId = ?2")
    List getApplysByResultSubId(Integer result, Integer subId);



    Integer countBySubIdAndResult(Integer subId, Integer result);

    @Query("select new com.ini.data.schema.ApplyUserSet(a,u) from Apply a, User u where " +
            "u.userId = ?1 and u.applyId = a.applyId")
    ApplyUserSet getLatestApplysByUserId(Integer userId);
}
