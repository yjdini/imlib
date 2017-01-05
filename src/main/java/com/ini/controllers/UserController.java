package com.ini.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ini.entity.User;
import com.mongodb.WriteResult;

@RestController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private MongoOperations mongoTemplate;
//
//	@RequestMapping("/adduser")
//	public
}
