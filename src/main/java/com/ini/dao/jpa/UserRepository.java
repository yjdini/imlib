package com.ini.dao.jpa;

import com.ini.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
