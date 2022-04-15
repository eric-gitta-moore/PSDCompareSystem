package com.example.newcompare.common.utils;

import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 使用七牛云进行文件存储
 */
public class QiniuCloudUtil {

    // 设置需要操作的账号的AK和SK					（AK和SK均在七牛云中获得，以下会说明）
    private static final String ACCESS_KEY = "J_SKCySTuI2WdOVfNea_845uT2fyMD4RJxPjeemc";
    private static final String SECRET_KEY = "9eGzVLp9aOcXYsk_YHbN4RQ4inYkxMSezzIJbYMp";

    // 要上传的空间								（刚刚新建空间的名称）
    private static final String bucketname = "nosugar-buchitang";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);


    private static final Integer expireSeconds = 3600;
	//新建空间时，七牛云分配出的域名 （自己可在万网购买域名解析后，绑定到加速域名）
    private static final String DOMAIN = "qiniu.buchitang.top/";

    public static String getUpToken() {
        return auth.uploadToken(bucketname, null, expireSeconds, new StringMap().put("insertOnly", 1));
    }
    public static String getBucket()
    {
        return bucketname;
    }
    public static Integer getExpireSeconds()
    {
        return expireSeconds;
    }
    public static Auth getAuth()
    {
        return auth;
    }



    /**
     * base64方式上传
     * @param base64
     * @param key 文件名
     * @return
     * @throws Exception
     */
    public static String put64image(byte[] base64, String key) throws Exception {
        String file64 = Base64.encodeToString(base64, 0);
        Integer len = base64.length;

        //华北空间使用 upload-z1.qiniu.com，华南空间使用 upload-z2.qiniu.com，北美空间使用 upload-na0.qiniu.com
        String url = "http://upload-z2.qiniu.com/putb64/" + len + "/key/" + UrlSafeBase64.encodeToString(key);

        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        //返回图片地址   https://q5jhgxz4q.bkt.clouddn.com/812bbd78-62d3-44bc-836c-9ee27ba4866a
        //用此地址可在网页中访问到上传的图片
        return DOMAIN + key;        
    }

    /**
     * @param key 图片的文件名
     * @Explain 删除空间中的图片
     */
    public static void delete(String key) {
        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(bucketname, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}