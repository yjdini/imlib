package com.ini.service.implement;

import com.ini.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */
public class FileSerivceImpl implements FileService {

    private static String path = "/User/yjdini/qujingboot/avatar/";
    private static String url = "";

    @Override
    public String generateFileName(String originalFilename) {
        String[] name = originalFilename.split(".");
        if (name.length == 2) {
            return "f" + System.currentTimeMillis() + "." + name[1];
        } else {
            return "f" + System.currentTimeMillis() + ".jpg";
        }
    }

    @Override
    public String saveFile(String fileName, MultipartFile file) throws Exception {
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(new File(path + fileName)));
        stream.write(file.getBytes());
        return url + fileName;
    }

    @Override
    public String saveFile(MultipartFile file) throws Exception {
        return saveFile( generateFileName( file.getOriginalFilename() ) , file);
    }
}
