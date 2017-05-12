package com.ini.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */
public class FileUploadUtil {

    private static String path = "/User/yjdini/qujingboot/avatar/";
    private static String url = "";

    public String generateFileName(String originalFilename) {
        String[] name = originalFilename.split(".");
        if (name.length == 2) {
            return "f" + System.currentTimeMillis() + "." + name[1];
        } else {
            return "f" + System.currentTimeMillis() + ".jpg";
        }
    }

    public String saveFile(String fileName, MultipartFile file) throws Exception {
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(new File(path + fileName)));
        stream.write(file.getBytes());
        return url + fileName;
    }

    public String saveFile(MultipartFile file) throws Exception {
        return saveFile( generateFileName( file.getOriginalFilename() ) , file);
    }
}
