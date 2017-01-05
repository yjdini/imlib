package com.db.mongod.gridfs.wrapper;

import java.util.HashMap;

import com.db.mongod.core.MongoClientFactory;
import com.db.mongod.core.config.Configuration;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;

/**
 * 
 * 
 *
 */
public final class GridFsFactory
{
	/**
	 * each GridFS distinct by bucket
	 * and from a same database which is configured in Configuration.
	 */
	private final static HashMap<String, GridFS> fsMap = new HashMap<String, GridFS>();
	private static String dbName = new Configuration().gridfsDbName;
	
	public GridFS getGridFs()
	{
		return getGridFs(GridFS.DEFAULT_BUCKET);
	}
	
	public GridFS getGridFs(String bucket)
	{
		GridFS fs = fsMap.get(bucket);
		if(fs == null)
		{
			DB db = MongoClientFactory.getMongoClient().getDB(dbName);
			fs = new GridFS(db, bucket);
			fsMap.put(bucket, fs);
		}
		return fs;
	}
	
	public void setDbName(String dbName)
	{
		GridFsFactory.dbName = dbName;
	}
}
