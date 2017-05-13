package com.ini.data.jpa;

import com.ini.data.entity.OpenSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by Somnus`L on 2017/5/12.
 */
public interface OpenSubRepository extends JpaRepository<OpenSub, Integer>, QueryByExampleExecutor<OpenSub> {

}
