package com.ini.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.mongod.core.MongoClientFactory;
import com.ini.entity.User;
import com.ini.entity.UserRepository;
import com.mongodb.MongoClient;

import java.util.List;

@RestController
@RequestMapping("/repository")
public class MongoRepositoryController
{
	@Autowired
	private UserRepository repository;
	
	@RequestMapping("/findbyname/{name}")
	public User findbyname(@PathVariable String name)
	{
		
		MongoClient mc = MongoClientFactory.getMongoClient();
		return repository.findByName(name);
	}
	
	@RequestMapping("/findbynamemany/{name}")
	public List<User> findbynamemany(@PathVariable String name)
	{
		return repository.findByName(name,true);
	}
	
	@RequestMapping("/count")
	public long count()
	{
		return repository.count();
	}
}
