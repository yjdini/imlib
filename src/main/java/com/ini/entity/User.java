package com.ini.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {  
	@Id
    private String id;

    private String name;

    private int age;

    private boolean sex;

    private char type;

    private char status;
}