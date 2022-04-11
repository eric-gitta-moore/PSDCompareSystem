package com.example.newcompare.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件处理
 */
public class FileUtil {
    //默认存放路径
    private static String storePath = "img";

    private static String catalogue = "src/main/resources/static/";

    public static String getCatalogue() {
        return catalogue;
    }

    /**
     * 存放图片，返回图片地址
     * @param route
     * @param file
     * @return 文件路径
     * @throws IOException
     */
    public static String pictureStorage(String route,MultipartFile file) throws IOException {
        byte [] bytes = file.getBytes();
        String imgName = UUID.randomUUID().toString();
        try {
            String url = QiniuCloudUtil.put64image(bytes,imgName);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 存放在static下的自定义的文件夹中
     *
     * @param file
     * @return 文件路径
     * @throws IOException
     */
    public static String pictureStorage(MultipartFile file) throws IOException {
        return pictureStorage(storePath, file);
    }


    /**
     * 处理多文件
     *
     * @param route
     * @param file
     * @return 文件路径数组
     */
    public static String[] pictureStorage(String route, MultipartFile[] file) throws IOException {
        String[] strings = new String[file.length];


        for (int i = 0; i < file.length; i++) {
            strings[i] = pictureStorage(file[i]);
        }
        return strings;
    }

    /**
     *
     *
     * @param file
     * @return 文件路径数组
     * @throws IOException
     */
    public static String[] pictureStorage(MultipartFile[] file) throws IOException {
        return pictureStorage(storePath, file);
    }

    /**
     * 计算文件的大小
     * @param path
     * @return
     * @throws IOException
     */
    public static String fileSize(String path) throws IOException {
        final File file = new File(path);
        final FileReader reader = new FileReader(file);
        int n=0;
        while (reader.read()!=-1){
            n++;
        }
        return String.format("%.2f",n*1.0/1000/1000);
    }

}
