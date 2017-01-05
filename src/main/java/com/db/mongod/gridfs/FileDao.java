package com.db.mongod.gridfs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

public interface FileDao
{
	/**
	 * @return the id generated by mongo.
	 * 
	 */
	String save(File file) throws IOException;
	String save(InputStream inputStream, String filename);
	
	/**
	 * set the chunkSize to store file.
	 * @param chunkSize
	 * @return
	 */
	FileDao setChunkSize(int chunkSize);
	
	/**
	 * interface of retrieving the file
	 */
	GridFSDBFile getFile(ObjectId id);
	GridFSDBFile getFile(DBObject query);
	GridFSDBFile getFile(String filename);
	byte[] getFileAsBytes(String string);
	List<GridFSDBFile> getFiles(DBObject query);
	
}
