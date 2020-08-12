package com.room302.shop.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public Boolean isImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        int i = fileName.lastIndexOf('.');
        String fileType = fileName.substring(i + 1);
        if (fileType.equals("JPG") || fileType.equals("jpg") ||
                fileType.equals("png") || fileType.equals("gif") ||
                fileType.equals("tif") || fileType.equals("bmp") ||
                fileType.equals("jpeg")) {
            return true;
        }
        return false;
    }
}
