package com.db.mongod.core;

import com.db.mongod.core.config.Configuration;
import com.mongodb.client.MongoDatabase;

/**
 *
 * get DataBase instance from the MongoClient
 *
 */
public final class DataBaseFactory
{
	private static String defaultDatabaseName = new Configuration().defaultDbName;
	
	public static MongoDatabase getDataBase(String databaseName)
	{
		return MongoClientFactory.getMongoClient().getDatabase(databaseName);
	}
	
	public static MongoDatabase getDataBase()
	{
		return MongoClientFactory.getMongoClient().getDatabase(defaultDatabaseName);
	}
	
	
}
