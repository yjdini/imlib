package com.ini.entity;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>
{
	public User findByName(String name);//mybatis 方式
	public List<User> findByName(String name, boolean many);
}
