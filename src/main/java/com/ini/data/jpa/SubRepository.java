package com.ini.data.jpa;

import com.ini.data.entity.Sub;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.lang.annotation.Native;
import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 */
public interface SubRepository extends JpaRepository<Sub, Integer>, QueryByExampleExecutor<Sub> {
    Sub findByToken(String token);

    @Query("select a.subId from Admin a where a.status = 1")
    List<Integer> getSubIds();
}
