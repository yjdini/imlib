package com.ini.data.jpa;

import com.ini.data.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {
    Integer countBySubId(Integer subId);

    Integer countBySubIdAndType(Integer subId, String type);

    Long countByType(String type);

    @Query("select u from User u where u.subId = ?1 and (u.type = 'm' or u.type='m-c') ")
    List<User> pageQueryMasters(Integer subId, Pageable pageRequest);

    @Query("select count(*) from User u where u.subId = ?1 and (u.type = 'm' or u.type='m-c') ")
    Long countMasters(Integer subId);

    List<User> findByTypeAndSubId(String type, Integer subId, Pageable pageRequest);

    Long countByTypeAndSubId(String type, Integer subId);
}
