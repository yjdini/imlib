package com.db.mongod.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.db.mongod.core.config.Configuration;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * maintain a singleton instance of MongoClient
 * static method too many? 
 */
public final class MongoClientFactory
{
	private static MongoClient singleClient;
	private final static ConnectConfig config = new ConnectConfig(); 
	
	public static MongoClient getMongoClient()
	{
		if(singleClient == null)
		{
			createMongoClient();
		}
		return singleClient;
	}

	private static void createMongoClient()
	{
		/**
		 * when the servers is configured, use it to create client
		 */
		if(config.servers == null)
		{
			singleClient = new MongoClient(config.host,config.port);
		}
		else
		{
			singleClient = new MongoClient(config.servers);
		}
	}
	
	/**
	 * method provided to override the default configuration in Configuration.
	 * @param host
	 * @param port
	 */
	public static void config(String host, int port)
	{
		config.host = host;
		config.port = port;
	}
	
	public static void config(List<ServerAddress> servers)
	{
		config.servers = servers;
	}
	
	
	/**
	 * a simple connect configuration
	 * without password & MongoCredential
	 *
	 */
	private static class ConnectConfig
	{
		private String host;
		private int port;
		private List<ServerAddress> servers;
		public ConnectConfig()
		{
			Configuration config = new Configuration();
			this.host = config.host;
			this.port = config.port;
			if(config.servers.length != 0)
			{
				ArrayList<ServerAddress> servers = new ArrayList<ServerAddress>();
				String host;
				int port;
				for(int i = 0, l = config.servers.length; i < l; i ++)
				{
					String[] arr = config.servers[i].split(":");
					host = arr[1];
					port = new Integer(arr[2]);
					servers.add(new ServerAddress(host, port));
				}
				this.servers = servers;
			}
		}
	}
}
