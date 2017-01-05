package com.ini.mongodemo;

import java.util.Date;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoPrimitive
{
	private static MongoClient client;
	private static MongoDatabase database;
	
	public static String getLastTime()
	{
		if(database == null)
		{
			initConnection();
		}
		
		MongoCollection<Document> collection = database.getCollection("access");
		FindIterable<Document> findIterable = collection.find(Filters.eq("appName", "Mongodb"));
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while(mongoCursor.hasNext())
		{  
			Document d = mongoCursor.next();
			long times;
			try
			{
				times = (Long) d.get("times");
			}
			catch(Exception e)//the times dont exist in the first time -->null pointer
			{
				return "";
			}
			finally
			{
				alterLastTime();
			}
			
			return new Date(times).toString();
		}
		
		return "";
	}
	
	private static void alterLastTime()
	{
		MongoCollection<Document> collection = database.getCollection("access");
		collection.updateMany(Filters.eq("appName", "Mongodb"), 
				new Document("$set", new Document("times", System.currentTimeMillis())));
		System.out.println("ok");
	}
	
	public static void addUser(String name)
	{
		if(database == null)
		{
			initConnection();
		}
		MongoCollection<Document> collection =  database.getCollection("user");
		collection.insertOne(new Document("name",name));
	}

	public static void initConnection()
	{
		if(client == null)
		{
//			Mongo m = new Mongo();@deprecated
			client = new MongoClient("localhost", 27017);
		}
		

		database = client.getDatabase("test");
		
		if(database.getCollection("user") == null)
		{
			database.createCollection("user");
		}
	}
}
