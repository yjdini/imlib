package com.db.mongod.gridfs.wrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;

import com.db.mongod.gridfs.FileDao;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class FileDaoImpl implements FileDao
{
	private final GridFS gridfs;
	
	public FileDaoImpl(){
		gridfs = new GridFsFactory().getGridFs();
	}
	public FileDaoImpl(String bucket){
		gridfs = new GridFsFactory().getGridFs(bucket);
	}
	
	@Override
	public String save(File file) throws IOException {
		GridFSInputFile f = gridfs.createFile(file);
		f.setFilename(file.getName());
		return save(f);
	}

	@Override
	public String save(InputStream inputStream, String filename) {
		GridFSInputFile f = gridfs.createFile(inputStream);
		f.setFilename(filename);
		return save(f);
	}
	
	private String save(GridFSInputFile file) {
		file.setChunkSize(this.chunksize);
		file.save();
		return file.getId().toString();
	}

	
	private int chunksize = GridFS.DEFAULT_CHUNKSIZE;
	
	@Override
	public FileDaoImpl setChunkSize(int chunksize){
		this.chunksize = chunksize;
		return this;
	}


	/**
	 * file.getLength() may be too big
	 * only suit for small file
	 */
	@Override
	public byte[] getFileAsBytes(String filename) {
		GridFSDBFile file = gridfs.findOne(filename);
		byte[] b = new byte[(int) file.getLength()];
		try {
			file.getInputStream().read(b);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	@Override
	public GridFSDBFile getFile(String filename) {
		return gridfs.findOne(filename);
	}
	@Override
	public GridFSDBFile getFile(ObjectId id) {
		return gridfs.find(id);
	}
	@Override
	public GridFSDBFile getFile(DBObject query) {
		return gridfs.findOne(query);
	}
	@Override
	public List<GridFSDBFile> getFiles(DBObject query) {
		return gridfs.find(query);
	}
}
