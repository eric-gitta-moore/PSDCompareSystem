package com.example.newcompare.common.utils;

import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUntils {

    /**
     * 压缩zip
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getZip(String filePath) throws Exception {
        File file = new File(filePath);
        String outputFIleName = file.getName() + ".zip";
        ArrayList<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            fileList.addAll(Arrays.asList(file.listFiles()));
        } else {
            fileList.add(file);
        }
        FileInputStream fileInputStream = null;
        //?
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(new FileOutputStream(new StringBuilder(filePath).delete(7,10).toString()+outputFIleName), new Adler32());
        ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);
        for (File f : fileList) {
            if (f.isDirectory()) {
                continue;
            }
            zipOutputStream.putNextEntry(new ZipEntry(f.getName()));
            fileInputStream = new FileInputStream(f);
            byte[] bytes = new byte[1024];
            int read;
            while ((read = fileInputStream.read(bytes)) != -1) {
                zipOutputStream.write(bytes);
            }

            fileInputStream.close();

        }


        zipOutputStream.close();

        return outputFIleName;
    }


    /**
     * 迭代删除文件夹
     * @param dirPath 文件夹路径
     */
    public static void deleteDir(String dirPath) throws IOException {
        File file = new File(dirPath);
        if (file.isFile()) {
             file.delete();
        } else {

            File[] files = file.listFiles();
            if (files == null) {
                file.delete();
            } else {
                 for(File q1:files){
                   deleteDir(q1.getPath());
                 }
                file.delete();
            }
        }
    }
    public static void dalete1(String path){
        File q1=new File(path);
        q1.delete();
    }

}
