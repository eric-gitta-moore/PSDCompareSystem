package com.example.compare.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileDownloadUtil {
    /**
     * http请求路径
     */
    private static String path = "http://139.9.203.100:9721/cadpare/download?workcode=";
    /**
     * 下载的压缩包路劲
     */
    private static String dir = "D:/tmp/";

    /**
     * 解压后文件所在位置
     */
    private static String desDirectory="D:/tmp";

    /**
     * 上传七牛云，生成url
     * @param workcode
     * @return
     * @throws Exception
     */
    public static String url(String workcode) throws Exception {
        String zip = getZip(workcode);
        String unzip = unzip(zip);
        FileInputStream inputStream = new FileInputStream(unzip);
        byte[] bytes = readInputStream(inputStream);
        String path = QiniuCloudUtil.put64image(bytes, UUID.randomUUID() + ".png");
        inputStream.close();
        new File(unzip).delete();
        new File(zip).delete();
        return path;
    }

//    public static void main(String[] args) throws Exception {
//        url("123124124124");
//    }


    /**
     * @author 徐启峰
     * @param zipFilePath
     * @return
     * @throws Exception
     */
    public static String unzip(String zipFilePath) throws IOException {

        File desDir = new File(desDirectory);
        if (!desDir.exists()) {
            boolean mkdirSuccess = desDir.mkdir();
            if (!mkdirSuccess) {
                throw new IOException("创建解压目标文件夹失败");
            }
        }
        // 读入流
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        // 遍历每一个文件
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        String unzipFilePath=null;
        //修改：刘锦堂，只去一张图片
        if (zipEntry != null) {
            if (zipEntry.isDirectory()) { // 文件夹
                unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                // 直接创建
                mkdir(new File(unzipFilePath));
            } else { // 文件
                unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                File file = new File(unzipFilePath);
                // 创建父目录
                mkdir(file.getParentFile());
                // 写出文件流
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(unzipFilePath));
                byte[] bytes = new byte[1024];
                int readLen;
                while ((readLen = zipInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, readLen);
                }
                bufferedOutputStream.close();
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
        return unzipFilePath;
    }

    // 如果父目录不存在则创建
    private static void mkdir(File file) {
        if (null == file || file.exists()) {
            return;
        }
        mkdir(file.getParentFile());
        file.mkdir();
    }

    /**
     * 从公司服务器获取对比之后的压缩包
     * @param workcode
     * @return
     * @throws IOException
     */
    public static String getZip(String workcode) throws IOException {

        URL url = new URL(path+workcode);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        final UUID uuid = UUID.randomUUID();

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        byte[] b= readInputStream(inputStream);
        String savePath = dir+uuid+".zip";
        File save = new File(savePath);
        FileOutputStream outputStream = new FileOutputStream(save);
        outputStream.write(b);
        outputStream.close();
        return savePath;
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    /**
     * 下载文件
     * @param workcode
     * @param response
     * @throws IOException
     */
    public static void downloadZip(String workcode, HttpServletResponse response) throws IOException {

        URL url = new URL(path+workcode);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        byte[] b= readInputStream(inputStream);
        response.getOutputStream().write(b);
    }

}
