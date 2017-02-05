package com.ini.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ini.entity.User;
import com.mongodb.WriteResult;

@RestController
@RequestMapping("/api/user")
public class UserController
{
	@Autowired
	private MongoOperations mongoTemplate;

	@RequestMapping(value ="/adduser",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody User user)
    {
        mongoTemplate.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}


