package com.example.newcompare.common.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CompareQueryUtil {

    /**
     * http请求对比状态路径
     */
    private static String path = "http://139.9.203.100:9721/cadpare/status?workcode=";

    /**
     * @author 徐启峰
     *  向公司的查询对比状态接口发送请求并返回状态码
     * @param workCode
     * @return responseCode
     * @throws IOException
     */
    public static int querycomparestatus(String workCode) throws IOException {
        URL url = new URL(path+workCode);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.connect();
        int responseCode = conn.getResponseCode();
        return responseCode;
    }
}
