package com.ini.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 *
 * Created by Somnus`L on 2017/5/11.
 */
public interface FileService {

    String generateFileName(String originalFilename);

    /**
     * return the saved file's url
     * @param fileName
     * @param image
     * @return
     */
    String saveFile(String fileName, MultipartFile image) throws FileNotFoundException, Exception;

    String saveFile(MultipartFile image) throws Exception;
}
