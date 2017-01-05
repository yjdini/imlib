package com.ini.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.db.mongod.gridfs.FileDao;
import com.db.mongod.gridfs.wrapper.FileDaoImpl;
import com.mongodb.gridfs.GridFSDBFile;

@Controller
@RequestMapping("/file")
public class FileController
{
	private FileDao fileDao = new FileDaoImpl();

	@RequestMapping(value = "/uploadFile",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadToMongo(@RequestParam("uploadfile") MultipartFile uploadfile)
	{
		String id = null;
		try {
			id = fileDao.save(uploadfile.getInputStream(), uploadfile.getName());
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(id, HttpStatus.OK);
	}
	
	@RequestMapping("/downloadFile")
	@ResponseBody
	public ResponseEntity<?> download() throws IOException
	{
		byte[] bytes = fileDao.getFileAsBytes("uploadfile");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_JPEG);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(bytes,header,HttpStatus.OK);
		return response;
	}

	@RequestMapping("/download")
	public void download2(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		GridFSDBFile f = fileDao.getFile("uploadfile");
		res.setStatus(HttpServletResponse.SC_OK);
		res.setContentLengthLong(f.getLength());
        res.setContentType(MediaType.IMAGE_JPEG_VALUE);
        
        f.writeTo(res.getOutputStream());
	}
}
