package com.ini.data.jpa;

import com.ini.data.entity.Admin;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface AdminRepository extends Repository<Admin, Integer>, QueryByExampleExecutor<Admin> {
    Admin findByNameAndPassword(String name, String password);
}
