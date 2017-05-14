package com.ini.utils;

import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */
public class FileUploadUtil {
    private final String rootPath;
    private final String avatarPath;
    private final String studentCardPath;

    public String uploadFile(MultipartFile file, String type) throws Exception {
        if (type.equals("avatar")) {
            return saveFile(avatarPath + generateFileName(file.getOriginalFilename()) , file, true);
        } else if (type.equals("studentCard")) {
            return saveFile(studentCardPath + generateFileName(file.getOriginalFilename()) , file, true);
        }
        return null;
    }


    /**
     * @param fileName the saved file's name
     * @param file the file object from request form-data
     * @param needCut is the picture need to be cutted
     * @return return file saved file's name
     */
    private String saveFile(String fileName, MultipartFile file, boolean needCut) throws Exception {
        if (!needCut) {
            return saveFile(fileName, file);
        } else {
            BufferedImage bufferedImage = ImageUtil.cutImage(file);
            if (bufferedImage != null) {
                ImageIO.write(bufferedImage, ImageUtil.getExt(file.getOriginalFilename()),
                        new File(rootPath + fileName));
            } else {
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(rootPath + fileName)));
                stream.write(file.getBytes());
            }
            return fileName;
        }
    }

    private String saveFile(String fileName, MultipartFile file) throws Exception {
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(new File(rootPath + fileName)));
        stream.write(file.getBytes());
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
        this.rootPath = env.getProperty("app.fileupload.root");
        this.avatarPath = env.getProperty("app.avatar.path");
        this.studentCardPath = env.getProperty("app.studentcard.path");
    }
}
