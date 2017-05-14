package com.ini.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by Somnus`L on 2017/5/14.
 *
 */
public class ImageUtil {
    public static BufferedImage cutImage(MultipartFile source) {
        if (source == null)
            return null;

        try {
            String ext = getExt(source.getOriginalFilename());
            InputStream is = source.getInputStream();

            BufferedImage bufferedImage = ImageIO.read(is);

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int x,y,w,h;
            if (width > height) {
                x = (width - height) / 2;
                y = 0;
                w = height;
                h = height;
            } else {
                x = 0;
                y = (height - width) / 2;
                w = width;
                h = width;
            }
            return bufferedImage.getSubimage(x,y,w,h);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getExt(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "jpg";
        } else {
          return fileName.substring(index + 1);
        }
    }
}
