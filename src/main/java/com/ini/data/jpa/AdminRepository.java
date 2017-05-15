package com.ini.data.jpa;

import com.ini.data.entity.Admin;
import com.ini.data.schema.SubSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface AdminRepository extends JpaRepository<Admin, Integer>, QueryByExampleExecutor<Admin> {
    Admin findByEmailAndPassword(String email, String password);

    @Query("select m from Admin m order by m.status desc")
    List<Admin> getAll();

    @Query("select new com.ini.data.schema.SubSet(a) from Admin a")
    List<SubSet> getSubTokenList();

    Admin findBySubId(Integer subId);
}
