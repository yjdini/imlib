package com.ini.utils;

import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */
public class FileUploadUtil {
    private String root;
    private String avatarPath;
    private String studentCardPath;


    public String saveFile(MultipartFile file, String type) throws Exception {
        if (type.equals("avatar")) {
            return saveFile(avatarPath + generateFileName(file.getOriginalFilename()) , file);
        } else if (type.equals("studentCard")) {
            return saveFile(studentCardPath + generateFileName(file.getOriginalFilename()) , file);
        }
        return null;
    }

    private String saveFile(String fileName, MultipartFile file) throws Exception {
//        BufferedOutputStream stream = new BufferedOutputStream(
//                new FileOutputStream(new File(root + fileName)));
//        stream.write(file.getBytes());
        BufferedImage bufferedImage = ImageUtil.cutImage(file);
        ImageIO.write(bufferedImage, ImageUtil.getExt(file.getOriginalFilename()),
                new File(root + fileName));
        return fileName;
    }

    private String generateFileName(String originalFilename) {
        String[] name = originalFilename.split(".");
        if (name.length == 2) {
            return "f" + System.currentTimeMillis() + "." + name[1];
        } else {
            return "f" + System.currentTimeMillis() + ".jpg";
        }
    }

    public FileUploadUtil(Environment env) {
        this.root = env.getProperty("app.fileupload.root");
        this.avatarPath = env.getProperty("app.avatar.path");
        this.studentCardPath = env.getProperty("app.studentcard.path");
    }
}
