package com.example.newcompare.controller;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 郑前
 * @Date: 2023/07/27/10:09
 * @Description:
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloader {
    public static void main(String[] args) {
        String fileURL = "http://localhost:8080/images/slide.jpg"; // 文件的URL
        String savePath = "D:\\Linux\\123.txt"; // 下载保存的路径

        String asas=null;
        try {
            URL url = new URL(fileURL);
            URLConnection conn = url.openConnection();

            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(savePath));

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            in.close();

            System.out.println("文件下载完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

